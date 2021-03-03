package com.smartosc.fintech.lms.controller;


import com.smartosc.fintech.lms.controller.handler.ApiError;
import com.smartosc.fintech.lms.dto.BriefLoanDto;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.LoanOfLenderDto;
import com.smartosc.fintech.lms.dto.PagingResponse;
import com.smartosc.fintech.lms.dto.RepaymentAmountDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.dto.SearchLoanDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/loan-application")
@Api(value = "Loan Application Api")
public interface LoanApplicationController {

    @ApiOperation(value = "get loan application by uuid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = LoanApplicationDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping("/{uuid}")
    public Response<LoanApplicationDto> getLoanApplication(@PathVariable String uuid);

    @ApiOperation(value = "get list of loan application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = LoanApplicationDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping()
    public Response<List<BriefLoanDto>> getListLoanApplication(@RequestParam long userId);

    @ApiOperation(value = "search loan application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LoanApplicationDto.class),
            @ApiResponse(code = 400, message = "Validation error", response = ApiError.class),
            @ApiResponse(code = 409, message = "From date can not greater than to date", response = ApiError.class),
            @ApiResponse(code = 500, message = "Page size must not be less than one!", response = ApiError.class),
    })
    @GetMapping("/filter")
    public Response<PagingResponse<LoanOfLenderDto>> getListLoanOfLender(@Valid SearchLoanDto filter);

    @ApiOperation(value = "get status of loan application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
    })
    @GetMapping("/status/{uuid}")
    Response<Integer> getStatus(@PathVariable String uuid);

    @ApiOperation(value = "get loan type of loan application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
    })
    @GetMapping("/loan-type")
    Response<List<String>> getLoanTypeList();

    @ApiOperation(value = "get repayment amount loan application")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
    })
    @GetMapping("/{uuid}/repayment-info")
    Response<RepaymentAmountDto> getRepaymentInfo(@PathVariable String uuid);
}
