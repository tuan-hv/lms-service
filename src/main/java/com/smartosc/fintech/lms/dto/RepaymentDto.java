package com.smartosc.fintech.lms.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class RepaymentDto {

    private long id;

    private String uuid;

    private Timestamp dueDate;

    private BigDecimal interestDue;

    private BigDecimal interestPaid;

    private Timestamp lastPaidDate;

    private Timestamp lastPenaltyAppliedDate;

    private BigDecimal penaltyDue;

    private BigDecimal penaltyPaid;

    private BigDecimal principalDue;

    private BigDecimal principalPaid;

    private BigDecimal feeDue;

    private BigDecimal feePaid;

    private Timestamp repaidDate;

    private String state;

    private String notes;

    private BigDecimal totalAmount;
}
