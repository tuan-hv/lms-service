package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.controller.FundingController;
import com.smartosc.fintech.lms.dto.DisbursementRequestDto;
import com.smartosc.fintech.lms.dto.FundingRequest;
import com.smartosc.fintech.lms.dto.PaymentResponse;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.service.EmailService;
import com.smartosc.fintech.lms.service.FundingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FundingControllerImpl implements FundingController {
    private final FundingService fundingService;
    private final EmailService emailService;

    @SMFLogger
    @Override
    public Response<Object> makeFunding(FundingRequest request) {
        LoanTransactionEntity transaction = fundingService.makeFunding(request);
        emailService.sendFundingEmail(transaction);
        return Response.ok(null);
    }

    @SMFLogger
    @Override
    public Response<String> getDisbursementUrl(DisbursementRequestDto request) {
        String response = fundingService.getDisbursementUrl(request);
        return Response.ok(response);
    }

    @SMFLogger
    @Override
    public Response<String> processDisbursementOnline(PaymentResponse paymentResponse) {
        LoanTransactionEntity transaction = fundingService.processDisbursementOnline(paymentResponse);
        emailService.sendFundingEmail(transaction);
        return Response.ok(transaction.getUuid());
    }
}
