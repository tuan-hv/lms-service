package com.smartosc.fintech.lms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.common.constant.PaymentGatewayStatus;
import com.smartosc.fintech.lms.common.constant.PaymentHistoryStatus;
import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.config.ApplicationConfig;
import com.smartosc.fintech.lms.dto.PaymentRequest;
import com.smartosc.fintech.lms.dto.PaymentResponse;
import com.smartosc.fintech.lms.dto.PaymentResultDto;
import com.smartosc.fintech.lms.dto.RepayRequestInPaymentServiceDto;
import com.smartosc.fintech.lms.entity.PaymentHistoryEntity;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import com.smartosc.fintech.lms.repository.PaymentHistoryRepository;
import com.smartosc.fintech.lms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import static com.smartosc.fintech.lms.common.constant.MessageKey.PAYMENT_GATEWAY_FAIL;
import static com.smartosc.fintech.lms.common.constant.MessageKey.PAYMENT_GATEWAY_TIMEOUT;

@Service
public class PaymentServiceImpl implements PaymentService {
    private RestTemplate restTemplate;
    private ApplicationConfig applicationConfig;
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    public PaymentServiceImpl(@Qualifier("RT") RestTemplate restTemplate,
                              ApplicationConfig applicationConfig,
                              PaymentHistoryRepository paymentRepository) {
        this.restTemplate = restTemplate;
        this.applicationConfig = applicationConfig;
        this.paymentHistoryRepository = paymentRepository;
    }

    @Override
    public void processFunding(PaymentRequest paymentRequest) {
        PaymentHistoryEntity history = new PaymentHistoryEntity();
        Timestamp creationDate = new Timestamp(new Date().getTime());
        history.setCreationDate(creationDate);
        history.setUuid(UUID.randomUUID().toString());
        history.setAmount(paymentRequest.getAmount());
        history.setBody(convertObject(paymentRequest));
        history.setUrl(applicationConfig.getPaymentGatewayUrl());

        try {
            ResponseEntity<PaymentResponse> response =
                    restTemplate.postForEntity(applicationConfig.getPaymentGatewayUrl(), paymentRequest, PaymentResponse.class);
            history.setResponse(convertObject(response));
            history.setStatus(PaymentHistoryStatus.SUCCESS.getValue());
            paymentHistoryRepository.save(history);
        } catch (ResourceAccessException ex) {
            handleTimeoutProcess(history, ex);
        } catch (Exception ex) {
            handleFailProcess(history, ex);
        }
    }

    @Override
    public PaymentResultDto<PaymentResponse> processRepayLoan(RepayRequestInPaymentServiceDto repayRequestInPaymentServiceDto) {
        PaymentResultDto<PaymentResponse> paymentResultDto = new PaymentResultDto<>();
        PaymentHistoryEntity history = new PaymentHistoryEntity();
        history.setCreationDate( new Timestamp(new Date().getTime()));
        history.setUuid(UUID.randomUUID().toString());
        history.setAmount(repayRequestInPaymentServiceDto.getAmount());
        history.setBody(convertObject(repayRequestInPaymentServiceDto));
        history.setUrl(applicationConfig.getRepaymentGatewayUrl());

        try {
            ResponseEntity<PaymentResponse> response =
                    restTemplate.postForEntity(applicationConfig.getRepaymentGatewayUrl(), repayRequestInPaymentServiceDto, PaymentResponse.class);
            history.setResponse(convertObject(response));
            paymentResultDto.setData(response.getBody());
            if (response.getStatusCodeValue() >= HttpStatus.BAD_REQUEST.value()) {
                history.setStatus(PaymentHistoryStatus.FAIL.getValue());
                paymentHistoryRepository.save(history);
                paymentResultDto.setFailed(true);
                throw new BusinessServiceException(ResourceUtil.getMessage(PAYMENT_GATEWAY_FAIL), ErrorCode.PAYMENT_GATEWAY_FAIL);
            }
            PaymentResponse body = response.getBody();
            if (body != null &&
                    PaymentGatewayStatus.SUCCESS.getValue() != body.getStatus().getCode()) {
                history.setStatus(PaymentHistoryStatus.FAIL.getValue());
                paymentHistoryRepository.save(history);
                paymentResultDto.setFailed(true);
                throw new BusinessServiceException(ResourceUtil.getMessage(PAYMENT_GATEWAY_FAIL), ErrorCode.PAYMENT_GATEWAY_FAIL);
            }
            history.setStatus(PaymentHistoryStatus.SUCCESS.getValue());
            paymentHistoryRepository.save(history);
            paymentResultDto.setSuccessful(true);
        } catch (ResourceAccessException ex) {
            handleTimeoutProcess(history, ex);
        } catch (Exception ex) {
            handleFailProcess(history, ex);
        }
        return paymentResultDto;
    }

    private String convertObject(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    private void handleTimeoutProcess(PaymentHistoryEntity history, ResourceAccessException ex) {
        history.setStatus(PaymentHistoryStatus.TIMEOUT.getValue());
        history.setMessage(ex.getMessage());
        paymentHistoryRepository.save(history);
        throw new BusinessServiceException(ResourceUtil.getMessage(PAYMENT_GATEWAY_TIMEOUT), ErrorCode.PAYMENT_GATEWAY_TIMEOUT);
    }

    private void handleFailProcess(PaymentHistoryEntity history, Exception ex) {
        history.setStatus(PaymentHistoryStatus.FAIL.getValue());
        history.setMessage(ex.getMessage());
        paymentHistoryRepository.save(history);
        throw new BusinessServiceException(ResourceUtil.getMessage(PAYMENT_GATEWAY_FAIL), ErrorCode.PAYMENT_GATEWAY_FAIL);
    }
}
