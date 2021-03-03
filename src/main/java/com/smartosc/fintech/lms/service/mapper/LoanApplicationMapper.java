package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.LoanApplicationDto;
import com.smartosc.fintech.lms.dto.LoanJobInformationDto;
import com.smartosc.fintech.lms.dto.LoanOfLenderDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanJobInformationEntity;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {LoanContactInformationMapper.class,
        LoanJobInformationMapper.class,
        LoanPersonalInformationMapper.class,
        PaymentAmountMapper.class,
        BigDecimalMapper.class,
        DateMapper.class
},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanApplicationMapper {

    static LoanApplicationMapper getInstance() {
       return Mappers.getMapper(LoanApplicationMapper.class);
    }

    @Mapping(source = "contractNumber", target = "accountNumber")
    @Mapping(source = "loanAmount", target = "loanAmount", qualifiedByName = "mapToBigDecimalScale")
    @Mapping(source = "status", target = "loanStatus")
    @Mapping(source = "interestRate", target = "interestRate", qualifiedByName = "mapStringToBigDecimalScale")
    @Mapping(source = "loanContactInformation", target = "loanContactInformation",
            qualifiedByName = "mapListContactInformationToDto")
    @Mapping(source = "loanJobInformation", target = "loanJobInformation",
            qualifiedByName = "mapToJobInformationDto")
    @Mapping(source = "createdDate", target = "createdDate",
            qualifiedByName = "mapTimestampToString")
    @Mapping(source = "signedOffDate", target = "signedOffDate",
            qualifiedByName = "mapTimestampToString")
    @Mapping(target = "loanStatusName",
            expression = "java(com.smartosc.fintech.lms.common.constant.LoanApplicationStatus.valueOf(loanApplicationEntity.getStatus()).getName())")
    @Mapping(target = "termUnit",
            expression = "java(com.smartosc.fintech.lms.common.constant.LoanTermPeriod.valueOf(loanApplicationEntity.getTermUnit()).getName())")
    @Mapping(source = "repaymentPeriodCount", target = "paymentFrequency")
    LoanApplicationDto mapToDto(LoanApplicationEntity loanApplicationEntity);

    @Mapping(source = "loanProduct.name", target = "loanType")
    @Mapping(source = "contractNumber", target = "loanAccount")
    @Mapping(source = "loanAmount", target = "amount")
    @Mapping(source = "loanTerm", target = "term")
    @Mapping(source = "interestRate", target = "rate")
    @Mapping(source = "loanPersonalInformation", target = "customerId", qualifiedByName = "mapToCustomerId")
    @Mapping(source = "loanPersonalInformation", target = "customerName", qualifiedByName = "mapToCustomerName")
    @Mapping(target = "termUnit",
            expression = "java(com.smartosc.fintech.lms.common.constant.LoanTermPeriod.valueOf(loanApplicationEntity.getTermUnit()).getName())")
    LoanOfLenderDto mapToLoanOfLenderDto(LoanApplicationEntity loanApplicationEntity);

    @Named("mapToCustomerId")
    static String mapToCustomerId(Collection<LoanPersonalInformationEntity> list) {
        LoanPersonalInformationEntity personalInfo = list.stream().findFirst().orElseGet(LoanPersonalInformationEntity::new);
        return personalInfo.getIdentificationCardNumber();
    }

    @Named("mapToCustomerName")
    static String mapToCustomerName(Collection<LoanPersonalInformationEntity> list) {
        LoanPersonalInformationEntity personalInfo = list.stream().findFirst().orElseGet(LoanPersonalInformationEntity::new);
        return personalInfo.getFullName();
    }

    @Named("mapInterestRateToInteger")
    static BigDecimal mapInterestRateToInteger(String interestRate) {
        return new BigDecimal(interestRate);
    }

    @Named("mapToJobInformationDto")
    static LoanJobInformationDto mapToJobInformationDto(Collection<LoanJobInformationEntity> list) {
        if (!CollectionUtils.isEmpty(list)) {
            List<LoanJobInformationEntity> sortedJobInfo = list.stream()
                    .sorted(Comparator.comparing(LoanJobInformationEntity::getLastUpdate).reversed())
                    .collect(Collectors.toList());
            return LoanJobInformationMapper.getInstance().toDto(sortedJobInfo.get(0));
        }
        return null;
    }

}
