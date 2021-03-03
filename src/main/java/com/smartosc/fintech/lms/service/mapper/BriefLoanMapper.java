package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.BriefLoanDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BriefLoanMapper {

    static BriefLoanMapper getInstance() {
        return Mappers.getMapper(BriefLoanMapper.class);
    }

    @Mapping(source = "contractNumber", target = "accountNumber")
    @Mapping(source = "loanProduct.name", target = "loanType")
    @Mapping(target = "statusName",
            expression = "java(com.smartosc.fintech.lms.common.constant.LoanApplicationStatus.valueOf(loanApplicationEntity.getStatus()).getName())")
    BriefLoanDto mapToDto(LoanApplicationEntity loanApplicationEntity);

    List<BriefLoanDto> mapToListBriefLoanDto(List<LoanApplicationEntity> loanApplicationEntities);

}
