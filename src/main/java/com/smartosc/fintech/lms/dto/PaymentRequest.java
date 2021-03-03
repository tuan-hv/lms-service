package com.smartosc.fintech.lms.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
    String applicationUuid;
    BigDecimal amount;
    String receivedAccount;
    String receivedBank;
    String sendAccount;
    String sendBank;
    String message;
}
