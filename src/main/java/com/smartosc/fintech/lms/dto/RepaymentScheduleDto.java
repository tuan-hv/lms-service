package com.smartosc.fintech.lms.dto;

import com.smartosc.fintech.lms.common.constant.CurrencyCode;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RepaymentScheduleDto {
    private String uuid;
    private String dueDate;
    private BigDecimal interestDue;
    private BigDecimal principalDue;
    private String state;
    private BigDecimal totalAmount;
    private CurrencyCode currency = CurrencyCode.VND;
}
