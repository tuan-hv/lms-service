package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.LoanFinInfoRequest;
import com.smartosc.fintech.lms.dto.LoanJobInformationDto;
import java.util.List;

public interface LoanFinInfoService {

    List<LoanJobInformationDto> listLoanFinInfo(String uuid);

    LoanJobInformationDto insertNewLoanFinInfo(String id, LoanFinInfoRequest request);

    LoanJobInformationDto getLastLoanFinInfo(String uuid);
}
