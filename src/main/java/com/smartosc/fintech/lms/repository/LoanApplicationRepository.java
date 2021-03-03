package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> , JpaSpecificationExecutor<LoanApplicationEntity> {
    Optional<LoanApplicationEntity> findLoanApplicationEntityByUuid(String uuid);

    @Query(value = "select *, case status\n" +
            "when 4 then 1\n" +
            "when 3 then 2\n" +
            "when 1 then 3\n" +
            "when 5 then 4\n" +
            "when 2 then 5\n" +
            "end as temp\n" +
            "from `lms-service`.loan_application\n" +
            "where status!=0\n" +
            "and user_id=?1\n" +
            "order by temp asc, last_updated_date desc;", nativeQuery = true)
    List<LoanApplicationEntity> findLoanApplicationEntityByUserIdAndStatusNotDrop(long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LoanApplicationEntity> findByUuid(String uuid);

    @Query(value="SELECT la.status FROM loan_application la WHERE la.uuid = :uuid", nativeQuery = true)
    List<Integer> getStatusOnly(@Param("uuid") String uuid);
}
