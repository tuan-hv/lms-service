package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.RepaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepaymentRepository extends JpaRepository<RepaymentEntity, Long>, JpaSpecificationExecutor<RepaymentEntity> {
    Optional<RepaymentEntity> findFirstByUuid(String uuid);

    List<RepaymentEntity> findByLoanApplicationUuidAndStateNotOrderByDueDateAsc(String loanKey, String state);

    List<RepaymentEntity> findByStateInAndLoanApplicationUuidOrderByDueDateAsc(String[] states, String uuid);


}
