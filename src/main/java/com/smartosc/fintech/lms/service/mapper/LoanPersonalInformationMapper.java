package com.smartosc.fintech.lms.service.mapper;


import com.smartosc.fintech.lms.common.util.DateTimeUtil;
import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.util.Collection;

@Mapper(uses = {LoanPersonalInformationMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanPersonalInformationMapper {

    static LoanPersonalInformationMapper getInstance() {
        return Mappers.getMapper(LoanPersonalInformationMapper.class);
    }

    @Mapping(source = "dateOfBirth", target = "dateOfBirth", qualifiedByName = "mapBirthdayToString")
    LoanPersonalInformationDto mapToDto(LoanPersonalInformationEntity loanPersonalInformationEntity);

    @Named("mapToListPersonalInformationDto")
    Collection<LoanPersonalInformationDto> mapToListPersonalDto(Collection<LoanPersonalInformationEntity> loanPersonalInformationEntity);

    @Named("mapBirthdayToString")
    static String mapBirthdayToString(Date date) {
        return DateTimeUtil.formatDate(date);
    }

}
