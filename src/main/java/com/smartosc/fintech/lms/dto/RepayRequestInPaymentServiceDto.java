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
public class RepayRequestInPaymentServiceDto {
    private String applicationUuid;
    private BigDecimal amount;
    private String message;
    private String receivedAccount;
    private String receivedBank;
    private String sendAccount;
    private String sendBank;
}
