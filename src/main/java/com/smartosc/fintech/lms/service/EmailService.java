package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import com.smartosc.fintech.lms.entity.RepaymentEntity;

public interface EmailService {
    void sendFundingEmail(LoanTransactionEntity transaction);
    void sendRepaymentEmail(RepaymentEntity repayment);
}
