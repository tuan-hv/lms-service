package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.RepaymentLatelyPrincipal;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;


@Mapper
public interface RepaymentLatelyPrincipalMapper extends BaseRepaymentMapper {

    static RepaymentLatelyPrincipalMapper getInstance() {
        return Mappers.getMapper(RepaymentLatelyPrincipalMapper.class);
    }

    @Override
    @Mapping(source = "loanApplication.loanProduct.name", target = "loanAccount")
    @Mapping(source = "loanApplication.loanProduct.loanPenaltyRate", target = "paidPrincipalLateRate")
    @Mapping(expression = "java( preDueDate )", target = "preDueDate")
    RepaymentLatelyPrincipal mapToBaseRepaymentDto(RepaymentEntity repaymentEntity, @Context LocalDateTime preDueDate);
}
