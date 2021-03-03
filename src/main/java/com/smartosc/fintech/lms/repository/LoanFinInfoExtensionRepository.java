package com.smartosc.fintech.lms.repository;


import com.smartosc.fintech.lms.dto.LoanFinInfoRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanFinInfoExtensionRepository {

    boolean updateByLoanApplicationId(LoanFinInfoRequest request, long loanApplicationId);
    boolean updateLastFinInfoByLoanApplicationId(LoanFinInfoRequest request, long loanApplicationId);
}
