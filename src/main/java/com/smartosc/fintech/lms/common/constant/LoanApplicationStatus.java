package com.smartosc.fintech.lms.common.constant;

import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static com.smartosc.fintech.lms.common.constant.ErrorCode.APPLICATION_STATUS_ERROR;
import static com.smartosc.fintech.lms.common.constant.MessageKey.APPLICATION_STATUS_INVALID;

@Getter
@AllArgsConstructor
public enum LoanApplicationStatus {
    DROP_OFF(0, "Drop"),
    APPROVED(1, "Approved"),
    REJECT(2, "Rejected"),
    SIGN(3, "Signed-off"),
    ACTIVE(4, "Active"),
    CLOSE(5, "Closed");

    private int value;
    private String name;

    public static LoanApplicationStatus valueOf(int value) {
        return Arrays.stream(values())
                .filter(status -> status.getValue() == value).findFirst()
                .orElseThrow(() -> new BusinessServiceException(ResourceUtil.getMessage(APPLICATION_STATUS_INVALID), APPLICATION_STATUS_ERROR));
    }
}
