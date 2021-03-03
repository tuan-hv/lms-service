package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.common.util.SMFLogger;
import com.smartosc.fintech.lms.dto.InputPersonalInformationDto;
import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;
import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import com.smartosc.fintech.lms.entity.LoanPersonalInformationEntity;
import com.smartosc.fintech.lms.repository.LoanApplicationRepository;
import com.smartosc.fintech.lms.repository.LoanFinInfoRepository;
import com.smartosc.fintech.lms.repository.PersonalInformationRepository;
import com.smartosc.fintech.lms.service.PersonalInformationService;
import com.smartosc.fintech.lms.service.mapper.LoanPersonalInformationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.smartosc.fintech.lms.common.constant.MessageKey.*;

@Service
@AllArgsConstructor
public class LoanInfoServiceImpl implements PersonalInformationService {

    private final PersonalInformationRepository personalInformationRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanFinInfoRepository personalFinInfoRepository;

    /**
     * get personal information by loan application with uuid
     *
     * @param uuid created by tuanhv2
     */
    @Override
    @SMFLogger
    public LoanPersonalInformationDto getLoanPersonalInformation(String uuid) {
        LoanPersonalInformationEntity loanPersonalInformation = personalInformationRepository.findByLoanApplicationUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ResourceUtil.getMessage(PERSONAL_INFO_NOT_FOUND), uuid)));
        return LoanPersonalInformationMapper.getInstance().mapToDto(loanPersonalInformation);
    }

    @Override
    @SMFLogger
    public List<LoanPersonalInformationDto> updateLoanPersonalInformation(String uuid, InputPersonalInformationDto inputPersonalInformationDto) {
        Optional<LoanApplicationEntity> optional = loanApplicationRepository.findLoanApplicationEntityByUuid(uuid);
        LoanApplicationEntity loanApplicationEntity = optional.orElseThrow(
                () -> new EntityNotFoundException(String.format(ResourceUtil.getMessage(APPLICATION_NOT_FOUND), uuid)));

        Collection<LoanPersonalInformationEntity> personalInformations = loanApplicationEntity.getLoanPersonalInformation();
        List<LoanPersonalInformationDto> loanPersonalInformationDtos = new ArrayList<>();
        for (LoanPersonalInformationEntity personalInformation : personalInformations) {
            personalInformation.setAddress(inputPersonalInformationDto.getAddress());
            personalInformation.setEmailAddress(inputPersonalInformationDto.getEmailAddress());
            personalInformationRepository.save(personalInformation);
            loanPersonalInformationDtos.add(LoanPersonalInformationMapper.getInstance().mapToDto(personalInformation));
        }
        return loanPersonalInformationDtos;
    }
}
