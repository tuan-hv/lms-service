package com.smartosc.fintech.lms.service;


import com.smartosc.fintech.lms.dto.*;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.RepaymentEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface RepaymentService {

    RepaymentResponseDto payBack(RepaymentRequestDto repaymentRequestDto);

    /**
     * Generate repayments form a loan application.
     *
     * @param loanApplicationDto a loan application
     * @return List<RepaymentDto> list of repayment
     */
    List<RepaymentDto> calculate(LoanApplicationDto loanApplicationDto);

    /**
     * Generate repayments form a loan application by loanApplicationId.
     *
     * @param loanApplicationUuid loan application uuid
     * @return List<RepaymentDto> list of repayment
     */
    List<RepaymentDto> calculate(String loanApplicationUuid);

    List<RepaymentDto> generateRepaymentPlan(LoanApplicationEntity application);

    /**
     * Calculate Accrued Interest
     *
     * @param loanApplicationEntity loan application
     * @return BigDecimal
     */
    BigDecimal calculateAccruedInterest(LoanApplicationEntity loanApplicationEntity, Timestamp fundedDate);

    RepaymentDto get(String id);

    void calculateAndSaveRepayment(RepaymentRequestDto repaymentRequestDto, RepaymentEntity repaymentEntity);

    RepaymentResponseDto processPayResult(PaymentResponse paymentResponse);

    PagingResponse<RepaymentScheduleDto> getRepaymentSchedule(RepaymentScheduleRequest request);
}
