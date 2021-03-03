package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.controller.RepaymentController;
import com.smartosc.fintech.lms.dto.*;
import com.smartosc.fintech.lms.service.RepaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class RepaymentControllerImpl implements RepaymentController {

    private final RepaymentService repaymentService;

    @Override
    public Response<RepaymentResponseDto> payBack(RepaymentRequestDto repaymentRequestDto) {
        return Response.ok(repaymentService.payBack(repaymentRequestDto));
    }

    @Override
    public Response<RepaymentDto> get(String id) {
        return Response.ok(repaymentService.get(id));
    }

    @Override
    public Response<RepaymentResponseDto> processPayResult(PaymentResponse paymentResponse) {
        return Response.ok(repaymentService.processPayResult(paymentResponse));
    }

    @Override
    public Response<PagingResponse<RepaymentScheduleDto>> getRepaymentSchedule(RepaymentScheduleRequest request) {
        return Response.ok(repaymentService.getRepaymentSchedule(request));
    }
}
