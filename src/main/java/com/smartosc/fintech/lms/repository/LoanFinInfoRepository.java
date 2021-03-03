package com.smartosc.fintech.lms.repository;


import com.smartosc.fintech.lms.entity.LoanJobInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanFinInfoRepository extends JpaRepository<LoanJobInformationEntity, Long> {

    Optional<LoanJobInformationEntity> findByLoanApplicationUuid(String uuid);

    @Query(value="SELECT * FROM loan_job_information l WHERE l.id in (:ids)", nativeQuery = true)
    List<LoanJobInformationEntity> searchByListId(@Param("ids") String idListAsString);

    @Query(value = "SELECT * FROM loan_job_information l WHERE l.loan_application_id = :loanAppId", nativeQuery = true)
    List<LoanJobInformationEntity> searchByLoanApplicationId(@Param("loanAppId") long loanAppId);

    @Query(value = "SELECT * " +
            "FROM loan_job_information lj " +
            "where lj.id = (select max(l1.id) from (SELECT l.id FROM loan_job_information l WHERE l.loan_application_id = :loanAppId) l1)" +
            "and lj.loan_application_id = :loanAppId", nativeQuery = true)
    LoanJobInformationEntity getLastFinInfoByLoanApplicationId(@Param("loanAppId") long loanAppId);
}
