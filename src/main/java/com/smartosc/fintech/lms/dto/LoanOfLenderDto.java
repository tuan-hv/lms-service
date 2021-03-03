package com.smartosc.fintech.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoanOfLenderDto {

    private long id;

    private String uuid;

    private String loanType;

    private Integer status;

    private String loanAccount;

    private String customerId;

    private String customerName;

    private String currency="VND";

    private BigDecimal amount;

    private String term;

    private String termUnit;

    private String rate;

    private String createdDate;

}
