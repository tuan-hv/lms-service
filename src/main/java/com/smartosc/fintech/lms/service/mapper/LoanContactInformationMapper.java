package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.common.util.DateTimeUtil;
import com.smartosc.fintech.lms.dto.LoanContactInformationDto;
import com.smartosc.fintech.lms.entity.LoanContactInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.util.Collection;

@Mapper(uses = {LoanContactInformationMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanContactInformationMapper {

    static LoanContactInformationMapper getInstance() {
        return Mappers.getMapper(LoanContactInformationMapper.class);
    }

    @Mapping(source = "refDob", target = "refDob", qualifiedByName = "mapBirthdayToString")
    @Mapping(target = "refTypeName",
            expression = "java(com.smartosc.fintech.lms.common.constant.ContactRefType.valueOf(loanContactInformationEntity.getRefType()).getName())")
    LoanContactInformationDto mapToDto(LoanContactInformationEntity loanContactInformationEntity);

    @Named("mapListContactInformationToDto")
    Collection<LoanContactInformationDto> mapToListContractDto(Collection<LoanContactInformationEntity> listContactInformation);

    @Named("mapBirthdayToString")
    static String mapBirthdayToString(Date date) {
        return DateTimeUtil.formatDate(date);
    }

}
