package com.smartosc.fintech.lms.service.impl;

import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.dto.BeneficiaryInfoDto;
import com.smartosc.fintech.lms.entity.BeneficiaryInfoEntity;
import com.smartosc.fintech.lms.entity.LoanDisbursementMethodEntity;
import com.smartosc.fintech.lms.repository.BeneficiaryInfoRepository;
import com.smartosc.fintech.lms.repository.LoanDisbursementMethodRepository;
import com.smartosc.fintech.lms.service.BeneficiaryInfoService;
import com.smartosc.fintech.lms.service.mapper.BeneficiaryInfoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static com.smartosc.fintech.lms.common.constant.MessageKey.BENEFICIARY_INFO_NOT_FOUND;
import static com.smartosc.fintech.lms.common.constant.MessageKey.DISBURSEMENT_METHOD_NOT_FOUND;

@Service
@AllArgsConstructor
public class BeneficiaryInfoServiceImpl implements BeneficiaryInfoService {
    private final BeneficiaryInfoRepository beneficiaryInfoRepository;
    private final LoanDisbursementMethodRepository loanDisbursementMethodRepository;

    @Override
    public BeneficiaryInfoDto getBeneficiaryInfoByLoanUUID(String uuid) {
        LoanDisbursementMethodEntity disbursementMethod = loanDisbursementMethodRepository.findByLoanApplicationUuid(uuid)
                .orElseThrow(() ->
                        new EntityNotFoundException(ResourceUtil.getMessage(DISBURSEMENT_METHOD_NOT_FOUND)));
        BeneficiaryInfoEntity beneficiaryInfo = beneficiaryInfoRepository.findByLoanDisbursementId(disbursementMethod.getId())
                .orElseThrow(() -> new EntityNotFoundException(ResourceUtil.getMessage(BENEFICIARY_INFO_NOT_FOUND)) );
        return BeneficiaryInfoMapper.getInstance().mapToDto(beneficiaryInfo);
    }
}
