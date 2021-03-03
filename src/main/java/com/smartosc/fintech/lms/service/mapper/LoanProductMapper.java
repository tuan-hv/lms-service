package com.smartosc.fintech.lms.service.mapper;


import com.smartosc.fintech.lms.dto.LoanProductDto;
import com.smartosc.fintech.lms.entity.LoanProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanProductMapper {

    static LoanProductMapper getInstance() {
        return Mappers.getMapper(LoanProductMapper.class);
    }

    @Named("mapLoanProductToDto")
    LoanProductDto mapToDto(LoanProductEntity loanProductEntity);
}
