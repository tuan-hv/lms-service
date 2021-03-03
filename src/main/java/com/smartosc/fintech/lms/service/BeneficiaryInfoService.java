package com.smartosc.fintech.lms.service;

import com.smartosc.fintech.lms.dto.BeneficiaryInfoDto;

public interface BeneficiaryInfoService {
    BeneficiaryInfoDto getBeneficiaryInfoByLoanUUID(String uuid);
}
