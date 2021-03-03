package com.smartosc.fintech.lms.controller;

import com.smartosc.fintech.lms.controller.handler.ApiError;
import com.smartosc.fintech.lms.dto.BeneficiaryInfoDto;
import com.smartosc.fintech.lms.dto.BeneficiaryInfoRequest;
import com.smartosc.fintech.lms.dto.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/beneficiary-info")
@Api(value = "Beneficiary information Api")
public interface BeneficiaryInfoController {
    @ApiOperation(value = "Get beneficiary information")
    @ApiResponses(value={
            @ApiResponse(code = 200,message = "Success",response = BeneficiaryInfoDto.class),
            @ApiResponse(code = 400, message = "Bad request", response = ApiError.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ApiError.class),
            @ApiResponse(code = 409, message = "Conflict Exception", response = ApiError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class)
    })
    @GetMapping()
    Response<BeneficiaryInfoDto> getBeneficiaryInfoByLoanUUID(@Valid BeneficiaryInfoRequest request);
}
