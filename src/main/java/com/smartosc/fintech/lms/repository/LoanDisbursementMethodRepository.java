package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.LoanDisbursementMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanDisbursementMethodRepository extends JpaRepository<LoanDisbursementMethodEntity, Long> {
    Optional<LoanDisbursementMethodEntity> findByLoanApplicationUuid(String uuid);
}
