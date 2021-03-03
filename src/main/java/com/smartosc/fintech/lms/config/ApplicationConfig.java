package com.smartosc.fintech.lms.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApplicationConfig {
    @Value("${payment.gateway.url}")
    private String paymentGatewayUrl;
    @Value("${repayment.gateway.url}")
    private String repaymentGatewayUrl;

    @Value("${disbursement.gateway.url}")
    private String disbursementGatewayUrl;

    @Value("${disbursement.return.endpoint}")
    private String disbursementReturnEndpoint;
}
