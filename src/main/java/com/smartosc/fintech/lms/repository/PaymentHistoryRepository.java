package com.smartosc.fintech.lms.repository;

import com.smartosc.fintech.lms.entity.PaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Long> {

}
