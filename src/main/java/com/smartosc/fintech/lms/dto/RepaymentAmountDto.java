package com.smartosc.fintech.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentAmountDto {
    private String loanAccount;
    private BigDecimal payOffAmount;
    private BigDecimal repaymentAmount;
}
