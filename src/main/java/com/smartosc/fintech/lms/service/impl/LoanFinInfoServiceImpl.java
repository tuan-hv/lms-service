package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.dto.LoanFinInfoRequest;
import com.smartosc.fintech.lms.dto.LoanJobInformationDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanJobInformationEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.LoanFinInfoExtensionRepository;
import com.smartosc.fintech.lms.repository.LoanFinInfoRepository;
import com.smartosc.fintech.lms.service.LoanFinInfoService;
import com.smartosc.fintech.lms.service.mapper.LoanJobInformationMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.smartosc.fintech.lms.common.constant.MessageKey.*;

@Service
@AllArgsConstructor
public class LoanFinInfoServiceImpl implements LoanFinInfoService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanFinInfoRepository finInfoRepository;
    private final LoanFinInfoExtensionRepository loanFinInfoExtensionRepository;

    @Override
    @SMFLogger
    public LoanJobInformationDto getLastLoanFinInfo(String uuid) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(
                () -> new EntityNotFoundException(String.format(ResourceUtil.getMessage(APPLICATION_NOT_FOUND), uuid)));
        LoanJobInformationEntity finInfoEntity = finInfoRepository.getLastFinInfoByLoanApplicationId(loanApplicationEntity.getId());
        if (ObjectUtils.isEmpty(finInfoEntity)) {
            throw new EntityNotFoundException(String.format(ResourceUtil.getMessage(FIN_INFO_NOT_FOUND), uuid));
        }
        return LoanJobInformationMapper.getInstance().toDto(finInfoEntity);
    }

    @Override
    @SMFLogger
    public LoanJobInformationDto insertNewLoanFinInfo(String uuid, LoanFinInfoRequest request) {
        double expense = Math.floor(Double.valueOf(request.getExpense()) * 100) / 100;
        double preTaxIncome =  Math.floor(Double.valueOf(request.getPreTaxIncome()) * 100) / 100;
        LoanJobInformationEntity newEntity = LoanJobInformationEntity.builder()
                .preTaxIncome(BigDecimal.valueOf(preTaxIncome))
                .companyAddress(request.getCompanyAddress())
                .companyName(request.getCompanyName())
                .expense(BigDecimal.valueOf(expense))
                .loanApplication(getLoanApplicationEntity(uuid))
                .lastUpdate(new Date())
                .build();
        finInfoRepository.save(newEntity);
        return LoanJobInformationMapper.getInstance().toDto(newEntity);
    }

    @Override
    @SMFLogger
    public List<LoanJobInformationDto> listLoanFinInfo(String uuid) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(
                () -> new EntityNotFoundException(String.format(ResourceUtil.getMessage(APPLICATION_NOT_FOUND), uuid)));
        return listLoanJobInfoByLoanApplication(loanApplicationEntity);
    }

    private List<LoanJobInformationDto> listLoanJobInfoByLoanApplication(LoanApplicationEntity loanApplicationEntity) {
        Collection<LoanJobInformationEntity> finInfoList = loanApplicationEntity.getLoanJobInformation();
        Collection<LoanJobInformationDto> updatedDto = LoanJobInformationMapper.getInstance().mapToListJobInformationDto(finInfoList);
        return updatedDto.stream().collect(Collectors.toCollection(ArrayList::new));
    }

    private LoanApplicationEntity getLoanApplicationEntity(String uuid) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        return optional.orElseThrow(() -> new EntityNotFoundException(String.format(ResourceUtil.getMessage(APPLICATION_NOT_FOUND), uuid)));
    }
}
