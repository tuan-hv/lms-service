package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.controller.LoanApplicationController;
import com.smartosc.fintech.lms.dto.BriefLoanDto;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.LoanOfLenderDto;
import com.smartosc.fintech.lms.dto.PagingResponse;
import com.smartosc.fintech.lms.dto.RepaymentAmountDto;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.dto.SearchLoanDto;
import com.smartosc.fintech.lms.repository.RepaymentRepository;
import com.smartosc.fintech.lms.service.LoanApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class LoanApplicationControllerImpl implements LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    RepaymentRepository repaymentRepository;


    @Override
    public Response<LoanApplicationDto> getLoanApplication(String uuid) {
        LoanApplicationDto loanApplicationDto = loanApplicationService.findLoanApplicationEntityByUuid(uuid);
        return Response.ok(loanApplicationDto);
    }

    @Override
    public Response<List<BriefLoanDto>> getListLoanApplication(long userId) {
        List<BriefLoanDto> loanApplicationDtos = loanApplicationService.findLoanApplicationByUser(userId);
        return Response.ok(loanApplicationDtos);
    }

    @Override
    public Response<PagingResponse<LoanOfLenderDto>> getListLoanOfLender(SearchLoanDto filter) {

        PagingResponse<LoanOfLenderDto> pagingResponse = loanApplicationService.searchListLoanOfLender(filter);

        return Response.ok(pagingResponse);
    }

    @Override
    public Response<Integer> getStatus(@PathVariable String uuid){
        return Response.ok(loanApplicationService.getStatusOnly(uuid));
    }

    @Override
    public Response<List<String>> getLoanTypeList() {
        return Response.ok(loanApplicationService.getLoanTypeList());
    }

    @Override
    public Response<RepaymentAmountDto> getRepaymentInfo(String uuid) {
        RepaymentAmountDto repaymentAmountDto= loanApplicationService.getRepaymentInfo(uuid);
        return Response.ok(repaymentAmountDto);
    }

}
