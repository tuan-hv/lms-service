package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.BeneficiaryInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeneficiaryInfoRepository extends JpaRepository<BeneficiaryInfoEntity, Long> {
    Optional<BeneficiaryInfoEntity> findByLoanDisbursementId(long disbursementId);
}
