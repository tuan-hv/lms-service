package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.controller.LoanFinanceInformationController;
import com.smartosc.fintech.lms.dto.LoanFinInfoRequest;
import com.smartosc.fintech.lms.dto.LoanJobInformationDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.service.LoanFinInfoService;
import com.smartosc.fintech.lms.validator.LoanFinInfoValidator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class LoanFinanceInformationControllerImpl implements LoanFinanceInformationController {

    private final LoanFinInfoService loanFinInfoService;
    private final LoanFinInfoValidator loanFinInfoValidator;
    @Override
    @SMFLogger
    public Response<Long> saveLoanFinInfo(String uuid, @Valid LoanFinInfoRequest request) {
        LoanJobInformationDto lastFinInfo = loanFinInfoService.getLastLoanFinInfo(uuid);
        loanFinInfoValidator.validateInputType(request);
        if(loanFinInfoValidator.isSimilarWithLast(lastFinInfo, request)){
            return Response.ok(lastFinInfo.getId());
        }

        LoanJobInformationDto result = loanFinInfoService.insertNewLoanFinInfo(uuid, request);
        return Response.ok(result.getId());
    }

    @Override
    @SMFLogger
    public Response<LoanJobInformationDto> fetchLastLoanFinInfo(String uuid) {
        return Response.ok(loanFinInfoService.getLastLoanFinInfo(uuid));
    }
}