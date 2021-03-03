package com.smartosc.fintech.lms.controller.impl;

import com.smartosc.fintech.lms.controller.LoanTransactionController;
import com.smartosc.fintech.lms.dto.LoanTransactionDto;
import com.smartosc.fintech.lms.dto.LoanTransactionRequest;
import com.smartosc.fintech.lms.dto.PagingResponse;
import com.smartosc.fintech.lms.dto.Response;
import com.smartosc.fintech.lms.service.LoanTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public class LoanTransactionControllerImpl implements LoanTransactionController {
    private final LoanTransactionService loanTransactionService;

    @Override
    public Response<LoanTransactionDto> getLoanTransaction(String uuid) {
        LoanTransactionDto response = loanTransactionService.getLoanTransaction(uuid);
        return Response.ok(response);
    }

    @Override
    public Response<PagingResponse<LoanTransactionDto>> getLoanTransactionsByLoanUUID(LoanTransactionRequest request) {
        PagingResponse<LoanTransactionDto> response = loanTransactionService.getListLoanTransaction(request);
        return Response.ok(response);
    }
}
