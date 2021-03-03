package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.dto.BeneficiaryInfoDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import com.smartosc.fintech.lms.service.BeneficiaryInfoService;
import com.smartosc.fintech.lms.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static com.smartosc.fintech.lms.common.constant.MessageKey.*;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final String EMAIL_FUNDING_TEMPLATE_NAME = "funding";
    private static final String EMAIL_REPAYMENT_TEMPLATE_NAME = "repayment";

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final BeneficiaryInfoService beneficiaryInfoService;

    @SMFLogger
    public void sendFundingEmail(LoanTransactionEntity transaction) {
        LoanApplicationEntity application = transaction.getLoanApplication();
        BeneficiaryInfoDto beneficiary = beneficiaryInfoService.getBeneficiaryInfoByLoanUUID(application.getUuid());
        LoanPersonalInformationEntity personalInfo = getPersonalInfo(application);

        Context context = new Context();
        context.setVariable("fullName", personalInfo.getFullName());
        context.setVariable("contractNumber", application.getContractNumber());
        context.setVariable("disbursementDate", transaction.getEntryDate());
        context.setVariable("disbursementAmount", transaction.getAmount());
        context.setVariable("bankAccount", beneficiary.getBeneficiaryAccount());
        context.setVariable("bankName", beneficiary.getBeneficiaryName());
        context.setVariable("bankCode", beneficiary.getBeneficiaryBank());
        context.setVariable("productName", application.getLoanProduct().getName());


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            String subject = String.format(ResourceUtil.getMessage(FUNDING_EMAIL_SUBJECT), application.getContractNumber());
            message.setSubject(subject);
            message.setTo(personalInfo.getEmailAddress());
            String htmlContent = templateEngine.process(EMAIL_FUNDING_TEMPLATE_NAME, context);
            message.setText(htmlContent, true);

            this.mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new BusinessServiceException(ResourceUtil.getMessage(EMAIL_SEND_FAIL), ErrorCode.SEND_EMAIL_FAIL);
        }
    }

    @SMFLogger
    public void sendRepaymentEmail(RepaymentEntity repayment) {
        LoanApplicationEntity application = repayment.getLoanApplication();
        LoanPersonalInformationEntity personalInfo = getPersonalInfo(application);

        Context context = new Context();
        context.setVariable("fullName", personalInfo.getFullName());
        context.setVariable("contractNumber", application.getContractNumber());
        context.setVariable("paymentDate", repayment.getLastPaidDate());

        BigDecimal paymentAmount = repayment.getPrincipalPaid().add(repayment.getInterestPaid());
        context.setVariable("paymentAmount", paymentAmount);
        context.setVariable("principalAmount", repayment.getPrincipalPaid());
        context.setVariable("interestAmount", repayment.getInterestPaid());

        BigDecimal loanBalance = application.getLoanAmount().subtract(application.getPrincipalDue());
        context.setVariable("loanBalance", loanBalance);


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            String subject = String.format(ResourceUtil.getMessage(REPAYMENT_EMAIL_SUBJECT), application.getContractNumber());
            message.setSubject(subject);
            message.setTo(personalInfo.getEmailAddress());
            String htmlContent = templateEngine.process(EMAIL_REPAYMENT_TEMPLATE_NAME, context);
            message.setText(htmlContent, true);

            this.mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new BusinessServiceException(ResourceUtil.getMessage(EMAIL_SEND_FAIL), ErrorCode.SEND_EMAIL_FAIL);
        }
    }

    private LoanPersonalInformationEntity getPersonalInfo(LoanApplicationEntity application) {
        return application.getLoanPersonalInformation().stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format(ResourceUtil.getMessage(PERSONAL_INFO_NOT_FOUND),
                                        application.getUuid())));
    }
}
