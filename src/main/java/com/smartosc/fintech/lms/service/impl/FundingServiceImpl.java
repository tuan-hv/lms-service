package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.constant.*;
import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.config.ApplicationConfig;
import com.smartosc.fintech.lms.dto.*;
import com.smartosc.fintech.lms.entity.LenderInfoEntity;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.LoanTransactionRepository;
import com.smartosc.fintech.lms.service.BeneficiaryInfoService;
import com.smartosc.fintech.lms.service.FundingService;
import com.smartosc.fintech.lms.service.PaymentService;
import com.smartosc.fintech.lms.service.RepaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.smartosc.fintech.lms.common.constant.MessageKey.*;

@Service
@AllArgsConstructor
public class FundingServiceImpl implements FundingService {
    private final ApplicationConfig applicationConfig;
    private final LoanApplicationRepository applicationRepository;
    private final LoanTransactionRepository transactionRepository;
    private final PaymentService paymentService;
    private final RepaymentService repaymentService;
    private final BeneficiaryInfoService beneficiaryInfoService;

    @Override
    @Transactional
    public LoanTransactionEntity makeFunding(FundingRequest request) {
        LoanApplicationEntity application = getLoanApplicationEntity(request.getApplicationUuid());

        PaymentRequest paymentRequest = createPaymentRequest(application);
        paymentService.processFunding(paymentRequest);

        return processDisbursement(application);
    }

    @Override
    public String getDisbursementUrl(DisbursementRequestDto request) {
        LoanApplicationEntity application = applicationRepository.findLoanApplicationEntityByUuid(request.getUuid()).orElseThrow(() ->
                new EntityNotFoundException(String.format(ResourceUtil.getMessage(APPLICATION_NOT_FOUND), request.getUuid())));
        BeneficiaryInfoDto beneficiary = beneficiaryInfoService.getBeneficiaryInfoByLoanUUID(application.getUuid());

        return buildPaymentGatewayUrl(application, beneficiary);
    }

    @Override
    @Transactional
    public LoanTransactionEntity processDisbursementOnline(PaymentResponse paymentResponse) {
        validateGatewayResponse(paymentResponse);
        LoanApplicationEntity application = getLoanApplicationEntity(paymentResponse.getData());
        return processDisbursement(application);
    }

    private LoanApplicationEntity getLoanApplicationEntity(String uuid) {
        Optional<LoanApplicationEntity> existApplication = applicationRepository.findByUuid(uuid);
        LoanApplicationEntity application = existApplication.orElseThrow(() ->
                new EntityNotFoundException(String.format(ResourceUtil.getMessage(APPLICATION_NOT_FOUND), uuid)));
        if (application.getStatus() != null && application.getStatus() != LoanApplicationStatus.SIGN.getValue()) {
            throw new EntityNotFoundException(String.format(ResourceUtil.getMessage(APPLICATION_NOT_FOUND), uuid));
        }
        return application;
    }

    private LoanTransactionEntity processDisbursement(LoanApplicationEntity application) {
        LoanTransactionEntity transaction = createTransactionEntity(application);
        transactionRepository.save(transaction);

        List<RepaymentDto> repaymentDtos = generateRepaymentPlan(application);
        BigDecimal principalDue = repaymentDtos.stream()
                .map(RepaymentDto::getPrincipalDue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal interestDue = repaymentDtos.stream()
                .map(RepaymentDto::getInterestDue).reduce(BigDecimal.ZERO, BigDecimal::add);

        application.getLoanTransactions().add(transaction);
        application.setStatus(LoanApplicationStatus.ACTIVE.getValue());
        application.setPrincipalDue(principalDue);
        application.setInterestDue(interestDue);
        applicationRepository.save(application);
        return transaction;
    }

    private LoanTransactionEntity createTransactionEntity(LoanApplicationEntity application) {
        LoanTransactionEntity transaction = new LoanTransactionEntity();

        Timestamp currentDate = new Timestamp(new Date().getTime());
        transaction.setCreationDate(currentDate);
        transaction.setEntryDate(currentDate);

        transaction.setUuid(UUID.randomUUID().toString());
        transaction.setLoanApplication(application);
        transaction.setType(LoanTransactionType.DISBURSEMENT.name());
        transaction.setAmount(application.getLoanAmount());

        return transaction;
    }

    private PaymentRequest createPaymentRequest(LoanApplicationEntity application) {
        PaymentRequest request = new PaymentRequest();
        request.setApplicationUuid(application.getUuid());
        request.setAmount(application.getLoanAmount());

        BeneficiaryInfoDto beneficiary = beneficiaryInfoService.getBeneficiaryInfoByLoanUUID(application.getUuid());
        request.setReceivedAccount(beneficiary.getBeneficiaryAccount());
        request.setReceivedBank(beneficiary.getBeneficiaryBank());

        LenderInfoEntity lender = application.getLenderInfoEntities().stream().findFirst()
                                    .orElseThrow(() -> new EntityNotFoundException(ResourceUtil.getMessage(LENDER_INFO_NOT_FOUND)));
        request.setSendAccount(lender.getAccount());
        request.setSendBank(lender.getBankCode());

        String message = String.format(ResourceUtil.getMessage(PAYMENT_FUNDING_MESSAGE), application.getUuid());
        request.setMessage(message);

        return request;
    }

    private List<RepaymentDto> generateRepaymentPlan(LoanApplicationEntity application) {
        return repaymentService.generateRepaymentPlan(application);
    }

    private String buildPaymentGatewayUrl(LoanApplicationEntity application, BeneficiaryInfoDto beneficiary){
        String beneficiaryBank = UriUtils.encode(beneficiary.getBeneficiaryBank(), StandardCharsets.UTF_8);
        String beneficiaryAccount = UriUtils.encode(beneficiary.getBeneficiaryAccount(), StandardCharsets.UTF_8);
        String beneficiaryName = UriUtils.encode(beneficiary.getBeneficiaryName(), StandardCharsets.UTF_8);
        LoanPersonalInformationEntity personalInfo = getPersonalInfo(application);
        StringBuilder paymentUrlBuilder = new StringBuilder().append(applicationConfig.getDisbursementGatewayUrl());
        paymentUrlBuilder.append(PaymentGatewayConstants.PARAM_START)
                .append(PaymentGatewayConstants.UUID_PARAM).append(PaymentGatewayConstants.EQUAL).append(application.getUuid())
                .append(PaymentGatewayConstants.AND)
                .append(PaymentGatewayConstants.BENEFICIARY_BANK_PARAM).append(PaymentGatewayConstants.EQUAL).append(beneficiaryBank)
                .append(PaymentGatewayConstants.AND)
                .append(PaymentGatewayConstants.BENEFICIARY_ACCOUNT_PARAM).append(PaymentGatewayConstants.EQUAL).append(beneficiaryAccount)
                .append(PaymentGatewayConstants.AND)
                .append(PaymentGatewayConstants.BENEFICIARY_NAME_PARAM).append(PaymentGatewayConstants.EQUAL).append(beneficiaryName)
                .append(PaymentGatewayConstants.AND)
                .append(PaymentGatewayConstants.AMOUNT_PARAM).append(PaymentGatewayConstants.EQUAL).append(application.getLoanAmount())
                .append(PaymentGatewayConstants.AND)
                .append(PaymentGatewayConstants.EMAIL_PARAM).append(PaymentGatewayConstants.EQUAL).append(personalInfo.getEmailAddress())
                .append(PaymentGatewayConstants.AND)
                .append(PaymentGatewayConstants.RETURN_URL_PARAM).append(PaymentGatewayConstants.EQUAL).append(applicationConfig.getDisbursementReturnEndpoint());
        return paymentUrlBuilder.toString();
    }

    private void validateGatewayResponse(PaymentResponse paymentResponse){
        if(paymentResponse.getData() == null || paymentResponse.getData().trim().isEmpty()){
            throw new BusinessServiceException(ResourceUtil.getMessage(DISBURSEMENT_INVALID_UUID), ErrorCode.DISBURSEMENT_UUID_ERROR);
        }
        if(PaymentGatewayStatus.SUCCESS.getValue() != paymentResponse.getStatus().getCode()) {
            throw new BusinessServiceException(ResourceUtil.getMessage(DISBURSEMENT_INVALID_STATUS), ErrorCode.DISBURSEMENT_STATUS_ERROR);
        }
    }

    private LoanPersonalInformationEntity getPersonalInfo(LoanApplicationEntity application) {
        return application.getLoanPersonalInformation().stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format(ResourceUtil.getMessage(PERSONAL_INFO_NOT_FOUND),
                        application.getUuid())));
    }
}
