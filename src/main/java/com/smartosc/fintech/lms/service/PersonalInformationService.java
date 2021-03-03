package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.InputPersonalInformationDto;
import com.smartosc.fintech.lms.dto.LoanPersonalInformationDto;

import java.util.List;

public interface PersonalInformationService {

    LoanPersonalInformationDto getLoanPersonalInformation(String uuid);

    List<LoanPersonalInformationDto>
    updateLoanPersonalInformation(String id, InputPersonalInformationDto loanPersonalInformation);
}
