package com.smartosc.fintech.lms.common.constant;

import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static com.smartosc.fintech.lms.common.constant.ErrorCode.DISBURSEMENT_METHOD_STATUS_ERROR;
import static com.smartosc.fintech.lms.common.constant.MessageKey.DISBURSEMENT_METHOD_INVALID;

@Getter
@AllArgsConstructor
public enum DisbursementMethod {
    CASH(0, "Cash"),
    TRANSFER(1, "Transfer");

    private int value;
    private String label;

    public static DisbursementMethod valueOf(int value) {
        return Arrays.stream(values())
                .filter(status -> status.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new BusinessServiceException(
                        ResourceUtil.getMessage(DISBURSEMENT_METHOD_INVALID),
                        DISBURSEMENT_METHOD_STATUS_ERROR));
    }
}
