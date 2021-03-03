package com.smartosc.fintech.lms.common.constant;

import lombok.Getter;

@Getter
public enum PaymentHistoryStatus {
    SUCCESS(0),
    FAIL(1),
    TIMEOUT(2);

    private int value;
    PaymentHistoryStatus(int value) {
        this.value = value;
    }
}
