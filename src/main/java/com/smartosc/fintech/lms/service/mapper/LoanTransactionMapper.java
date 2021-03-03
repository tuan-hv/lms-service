package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.common.util.DateTimeUtil;
import com.smartosc.fintech.lms.dto.LoanTransactionDto;
import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanTransactionMapper {

    static LoanTransactionMapper getInstance() {
        return Mappers.getMapper(LoanTransactionMapper.class);
    }

    @Mapping(source = "creationDate", target = "creationDate", dateFormat = DateTimeUtil.FORMAT_TIMESTAMP)
    @Mapping(source = "entryDate", target = "entryDate", dateFormat = DateTimeUtil.FORMAT_TIMESTAMP)
    LoanTransactionDto mapToDto(LoanTransactionEntity loanTransactionEntity);

    @Named("mapToListLoanTransactionDto")
    Collection<LoanTransactionDto> mapToListTransactionDto(Collection<LoanTransactionEntity> loanTransactionEntity);

}
