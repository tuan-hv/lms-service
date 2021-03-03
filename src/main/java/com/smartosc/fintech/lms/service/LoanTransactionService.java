package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.LoanTransactionDto;
import com.smartosc.fintech.lms.dto.LoanTransactionRequest;
import com.smartosc.fintech.lms.dto.PagingResponse;

public interface LoanTransactionService {
    LoanTransactionDto getLoanTransaction(String uuid);
    PagingResponse<LoanTransactionDto> getListLoanTransaction(LoanTransactionRequest request);
}
