package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.BaseRepaymentDto;
import com.smartosc.fintech.lms.entity.RepaymentEntity;

import java.time.LocalDateTime;

public interface BaseRepaymentMapper {

    BaseRepaymentDto mapToBaseRepaymentDto(RepaymentEntity repaymentEntity, LocalDateTime preDueDate);
}
