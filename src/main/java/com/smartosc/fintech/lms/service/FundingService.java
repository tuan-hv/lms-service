package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.DisbursementRequestDto;
import com.smartosc.fintech.lms.dto.FundingRequest;
import com.smartosc.fintech.lms.dto.PaymentResponse;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;

public interface FundingService {
    LoanTransactionEntity makeFunding(FundingRequest request);
    String getDisbursementUrl(DisbursementRequestDto request);
    LoanTransactionEntity processDisbursementOnline(PaymentResponse paymentResponse);
}
