package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.BeneficiaryInfoDto;
import com.smartosc.fintech.lms.entity.BeneficiaryInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BeneficiaryInfoMapper {

    static BeneficiaryInfoMapper getInstance() {
        return Mappers.getMapper(BeneficiaryInfoMapper.class);
    }
    BeneficiaryInfoDto mapToDto(BeneficiaryInfoEntity entity);
}
