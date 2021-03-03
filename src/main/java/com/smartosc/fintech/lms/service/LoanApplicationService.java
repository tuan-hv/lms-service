package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.BriefLoanDto;
import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.LoanOfLenderDto;
import com.smartosc.fintech.lms.dto.PagingResponse;
import com.smartosc.fintech.lms.dto.RepaymentAmountDto;
import com.smartosc.fintech.lms.dto.SearchLoanDto;

import java.util.List;

public interface LoanApplicationService {

    LoanApplicationDto findLoanApplicationEntityByUuid(String uuid);

    List<BriefLoanDto> findLoanApplicationByUser(long id);

    PagingResponse<LoanOfLenderDto> searchListLoanOfLender(SearchLoanDto searchLoanDto);
    Integer getStatusOnly(String uuid);

    List<String> getLoanTypeList();

    RepaymentAmountDto getRepaymentInfo(String uuid);


}
