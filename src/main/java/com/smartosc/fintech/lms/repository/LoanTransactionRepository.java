package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanTransactionRepository extends JpaRepository<LoanTransactionEntity, Long>,
                                                    JpaSpecificationExecutor<LoanTransactionEntity> {
    LoanTransactionEntity findDistinctFirstByLoanApplicationUuidAndType(String uuid, String type);
    Optional<LoanTransactionEntity> findByUuid(String uuid);
}
