package com.smartosc.fintech.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.smartosc.fintech.lms.common.constant.ParamsConstant.ZERO_2SCALE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BaseRepaymentDto {
    private String loanAccount;
    private LocalDateTime preDueDate;
    private LocalDateTime dueDate;
    private BigDecimal principalPaid = ZERO_2SCALE;
    private BigDecimal interestPaid = ZERO_2SCALE;
    private BigDecimal principalDue = ZERO_2SCALE;
    private BigDecimal interestDue = ZERO_2SCALE;

    public boolean checkExpireDueDate() {
        return false;
    }

    public boolean checkInPeriod() {
        return false;
    }

    public BigDecimal penaltyPaidPricipalEarly() {
        return ZERO_2SCALE;
    }

    public BigDecimal penaltyPaidInterestEarly() {
        return ZERO_2SCALE;
    }

    public BigDecimal penaltyPaidEarly() {
        return penaltyPaidPricipalEarly().add(penaltyPaidInterestEarly());
    }

    public BigDecimal penaltyPaidPricipalLately() {
        return ZERO_2SCALE;
    }

    public BigDecimal penaltyPaidInterestLately() {
        return ZERO_2SCALE;
    }

    public BigDecimal penaltyPaidLately() {
        return penaltyPaidPricipalLately().add(penaltyPaidInterestLately());
    }

    public BigDecimal leftPrincipal() {
        principalDue = principalDue == null ? ZERO_2SCALE : principalDue;
        principalPaid = principalPaid == null ? ZERO_2SCALE : principalPaid;
        return principalDue.subtract(principalPaid);
    }

    public BigDecimal leftInterest() {
        interestDue = interestDue == null ? ZERO_2SCALE : interestDue;
        interestPaid = interestPaid == null ? ZERO_2SCALE : interestPaid;
        return interestDue.subtract(interestPaid);
    }

    public BigDecimal paymentAmount() {
        return leftPrincipal().add(leftInterest()).add(penaltyPaidEarly()).add(penaltyPaidLately());
    }

}
