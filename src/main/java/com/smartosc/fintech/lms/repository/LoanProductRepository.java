package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.LoanProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanProductRepository extends JpaRepository<LoanProductEntity,Long> {

    @Query(value = "SELECT name FROM `lms-service`.loan_product;",nativeQuery = true)
    List<String> getLoanTypeList();
}
