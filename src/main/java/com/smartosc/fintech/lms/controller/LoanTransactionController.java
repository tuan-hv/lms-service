package com.smartosc.fintech.lms.controller;

import com.smartosc.fintech.lms.controller.handler.ApiError;
import com.smartosc.fintech.lms.dto.LoanTransactionDto;
import com.smartosc.fintech.lms.dto.LoanTransactionRequest;
import com.smartosc.fintech.lms.dto.PagingResponse;
import com.smartosc.fintech.lms.dto.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/transaction-history")
@Api(value = "Loan Transaction Api")
public interface LoanTransactionController {
    @ApiOperation(value = "Get loan transaction by uuid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = LoanTransactionDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping("/{uuid}")
    Response<LoanTransactionDto> getLoanTransaction(@PathVariable String uuid);

    @ApiOperation(value = "Get list of loan transaction")
    @ApiResponses(value={
            @ApiResponse(code = 200,message = "Success",response = PagingResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping()
    Response<PagingResponse<LoanTransactionDto>> getLoanTransactionsByLoanUUID(@Valid LoanTransactionRequest request);
}
