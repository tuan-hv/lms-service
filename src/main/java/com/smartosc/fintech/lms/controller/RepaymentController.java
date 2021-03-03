package com.smartosc.fintech.lms.controller;

import com.smartosc.fintech.lms.controller.handler.ApiError;
import com.smartosc.fintech.lms.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(path = "/repayments")
@Api(value = "Repayment API")
public interface RepaymentController {
    @ApiOperation(value = "Make a repayment transaction of a loan", notes = "id is mandatory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RepaymentResponseDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @PostMapping("/pay-back")
    Response<RepaymentResponseDto> payBack(@RequestBody RepaymentRequestDto repaymentRequestDto);

    @ApiOperation(value = "Get repayment detail", notes = "id is mandatory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RepaymentDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping("/{id}")
    Response<RepaymentDto> get(@PathVariable String uuid);

    @ApiOperation(value = "Process pay result of a repayment", notes = "id is mandatory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RepaymentResponseDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @PostMapping("/process-pay-result")
    Response<RepaymentResponseDto> processPayResult(@RequestBody PaymentResponse paymentResponse);

    @ApiOperation(value = "Get list of repayment schedule")
    @ApiResponses(value={
            @ApiResponse(code = 200,message = "Success",response = PagingResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping("/schedule")
    Response<PagingResponse<RepaymentScheduleDto>> getRepaymentSchedule(@Valid RepaymentScheduleRequest request);
}
