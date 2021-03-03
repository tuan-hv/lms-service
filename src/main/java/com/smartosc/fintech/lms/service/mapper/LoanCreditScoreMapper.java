package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.LoanCreditScoreDto;
import com.smartosc.fintech.lms.entity.LoanCreditScoreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface LoanCreditScoreMapper {

    static LoanCreditScoreMapper getInstance() {
        return Mappers.getMapper(LoanCreditScoreMapper.class);
    }

    LoanCreditScoreDto mapToDto(LoanCreditScoreEntity loanCreditScoreEntity);

    @Named("mapToListLoanCreditScoreDto")
    Collection<LoanCreditScoreDto> mapToListLoanCreditScoreDto(Collection<LoanCreditScoreEntity> loanCreditScoreEntity);

}
