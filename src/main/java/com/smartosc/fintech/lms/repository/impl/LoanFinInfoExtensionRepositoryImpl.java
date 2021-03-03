package com.smartosc.fintech.lms.repository.impl;


import com.smartosc.fintech.lms.common.constant.TableAndFieldName;
import com.smartosc.fintech.lms.dto.LoanFinInfoRequest;
import com.smartosc.fintech.lms.repository.LoanFinInfoExtensionRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;

@Repository
public class LoanFinInfoExtensionRepositoryImpl implements LoanFinInfoExtensionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean updateByLoanApplicationId(LoanFinInfoRequest request, long loanApplicationId) {
        String updateQuery = "update "+ TableAndFieldName.LOAN_JOB_INFORMATION.toString().toLowerCase() +" l " +
                "set l.company_address = ?1, l.company_name = ?2,l.pre_tax_income = ?3, l.expense = ?4, l.last_update = ?5 " +
                "where l.loan_application_id = ?6";
        Query query = entityManager.createNativeQuery(updateQuery);
        query.setParameter(1, request.getCompanyAddress());
        query.setParameter(2, request.getCompanyName());
        query.setParameter(3, request.getPreTaxIncome());
        query.setParameter(4, request.getExpense());
        query.setParameter(5, new Date());
        query.setParameter(6, loanApplicationId);
        entityManager.flush();
        Integer countUpdated = query.executeUpdate();
        entityManager.clear();
        return countUpdated > 0;
    }

    /**
     * use max(id) equals with last record
    **/
    @Transactional
    @Override
    public boolean updateLastFinInfoByLoanApplicationId(LoanFinInfoRequest request, long loanApplicationId) {
        String updateQuery = "update "+ TableAndFieldName.LOAN_JOB_INFORMATION.toString().toLowerCase() +" l " +
                "set l.company_address = ?1, l.company_name = ?2,l.pre_tax_income = ?3, l.expense = ?4, l.last_update = ?5 " +
                "where l.loan_application_id = ?6 and l.id = (select max(ll.id) from " + TableAndFieldName.LOAN_JOB_INFORMATION.toString().toLowerCase() +" ll) ";

        Query nativeQuery = entityManager.createNativeQuery(updateQuery);
        nativeQuery.setParameter(1, request.getCompanyAddress());
        nativeQuery.setParameter(2, request.getCompanyName());
        nativeQuery.setParameter(3, request.getPreTaxIncome());
        nativeQuery.setParameter(4, request.getExpense());
        nativeQuery.setParameter(5, new Date());
        nativeQuery.setParameter(6, loanApplicationId);
        entityManager.flush();
        Integer countUpdated = nativeQuery.executeUpdate();
        entityManager.clear();
        return countUpdated > 0;
    }

}
