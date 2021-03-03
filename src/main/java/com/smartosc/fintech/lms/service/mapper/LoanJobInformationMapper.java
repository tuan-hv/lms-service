package com.smartosc.fintech.lms.service.mapper;


import com.smartosc.fintech.lms.common.util.DateTimeUtil;
import com.smartosc.fintech.lms.dto.LoanFinInfoRequest;
import com.smartosc.fintech.lms.dto.LoanJobInformationDto;
import com.smartosc.fintech.lms.entity.LoanJobInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.sql.Date;
import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanJobInformationMapper {

    static LoanJobInformationMapper getInstance() {
        return Mappers.getMapper(LoanJobInformationMapper.class);
    }

    @Mapping(source = "lastUpdate", target = "latestUpdate", qualifiedByName = "convertDateToString")
    LoanJobInformationDto toDto(LoanJobInformationEntity loanJobInformationEntity);

    @Named("mapToListJobInformationDto")
    Collection<LoanJobInformationDto> mapToListJobInformationDto(Collection<LoanJobInformationEntity> loanJobInformationEntity);

    LoanJobInformationEntity toEntity(LoanFinInfoRequest request);

    @Named("convertDateToString")
    static String convertDateToString(Date date) {
        return DateTimeUtil.date2string(date, false);
    }
}

