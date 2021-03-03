package com.smartosc.fintech.lms.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BriefLoanDto {
    private String uuid;

    private String accountNumber;

    private BigDecimal outstandingBalance;

    private Integer status;

    private String statusName;

    private String loanType;

}
