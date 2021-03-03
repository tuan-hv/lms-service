package com.smartosc.fintech.lms.validator;

import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.dto.RepaymentRequestDto;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import org.springframework.stereotype.Component;

import static com.smartosc.fintech.lms.common.constant.MessageKey.REPAYMENT_VALIDATE_AMOUNT;
import static com.smartosc.fintech.lms.common.constant.MessageKey.REPAYMENT_VALIDATE_UUID;

@Component
public class RepaymentRequestValidator {
    public void validateRepaymentRequest(RepaymentRequestDto repaymentRequestDto){
        if(repaymentRequestDto.getAmount() == null){
            throw new BusinessServiceException(ResourceUtil.getMessage(REPAYMENT_VALIDATE_AMOUNT), ErrorCode.REPAYMENT_AMOUNT_EMPTY);
        }
        if(repaymentRequestDto.getUuid() == null || repaymentRequestDto.getUuid().isEmpty()){
            throw new BusinessServiceException(ResourceUtil.getMessage(REPAYMENT_VALIDATE_UUID), ErrorCode.REPAYMENT_UUID_EMPTY);
        }
    }
}
