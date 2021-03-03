package com.smartosc.fintech.lms.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class LoanProductDto implements Serializable {
    private long id;

    private String name;

    private String description;

    private Long minAmount;

    private Long maxAmount;

    private String interestRate;

    private Integer type;

    private String interestAccruedAccountingMethod;

    private String interestApplicationMethod;

    private String interestCalculationMethod;

    private String interestRateKey;

    private Integer fixedDaysOfMonth;

    private String penaltyCalculationMethod;

    private String paymentmethod;

    private String prepaymentAcceptance;

    private String prepaymentRecalculationMethod;

    private String principalPaidInstallmentStatus;

    private String principalPaymentKey;

    private String repaymentAllocationOrder;

    private Integer repaymentPeriodCount;

    private String repaymentPeriodUnit;

    private String repaymentScheduleMethod;

    private String taxCalculationMethod;

    private Integer status;

    private Timestamp createdDate;

    private Timestamp lastUpdatedDate;

    private Integer daysInYear;

    private String loanPenaltyRate;
}
