package com.smartosc.fintech.lms.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentGatewayStatus {
    SUCCESS(200),
    SERVER_ERROR(500);
    private int value;
}
