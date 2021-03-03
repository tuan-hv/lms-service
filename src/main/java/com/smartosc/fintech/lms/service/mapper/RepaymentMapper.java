package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.common.util.DateTimeUtil;
import com.smartosc.fintech.lms.dto.RepaymentDto;
import com.smartosc.fintech.lms.dto.RepaymentScheduleDto;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RepaymentMapper {

    static RepaymentMapper getInstance() {
        return Mappers.getMapper(RepaymentMapper.class);
    }

    RepaymentDto entityToDto(RepaymentEntity repaymentEntity);

    RepaymentEntity dtoToEntity(RepaymentDto repaymentDto);

    @Mapping(source = "dueDate", target = "dueDate", dateFormat = DateTimeUtil.FORMAT_TIMESTAMP)
    @Mapping(target = "totalAmount", expression = "java(repaymentEntity.getPrincipalDue().add(repaymentEntity.getInterestDue()))")
    RepaymentScheduleDto entityToScheduleDto(RepaymentEntity repaymentEntity);
}
