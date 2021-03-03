package com.smartosc.fintech.lms.controller;

import com.smartosc.fintech.lms.controller.handler.ApiError;
import com.smartosc.fintech.lms.dto.LoanFinInfoRequest;
import com.smartosc.fintech.lms.dto.LoanJobInformationDto;
import com.smartosc.fintech.lms.dto.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Api(value = "Loan financial information API")
@RequestMapping("loan-fininfo")
public interface LoanFinanceInformationController {
    @ApiOperation(value = "Update Loan financial information by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = LoanFinInfoRequest.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @PutMapping("/{uuid}")
    Response<Long> saveLoanFinInfo(@PathVariable String uuid, @Valid @RequestBody LoanFinInfoRequest request);

    @ApiOperation(value = "Get Loan financial information by loan application with uuid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = LoanJobInformationDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping("/{uuid}")
    Response<LoanJobInformationDto> fetchLastLoanFinInfo(@PathVariable("uuid") String uuid);
}
