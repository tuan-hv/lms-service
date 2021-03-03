package com.smartosc.fintech.lms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentResponse implements Serializable {
    @Data
    public static class PaymentResponseStatus implements Serializable {
        private int code;
        private String message;
    }

    private PaymentResponseStatus status;
    private String data;
}
