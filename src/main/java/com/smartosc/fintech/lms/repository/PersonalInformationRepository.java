package com.smartosc.fintech.lms.repository;


import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalInformationRepository extends JpaRepository<LoanPersonalInformationEntity, Long> {

    Optional<LoanPersonalInformationEntity> findByLoanApplicationUuid(String uuid);

    @Query(value = "SELECT * FROM `lms-service`.loan_personal_information where loan_application_id=?1", nativeQuery = true)
    List<LoanPersonalInformationEntity> getPersonalInformationByLoanApplicationId(long id);

}
