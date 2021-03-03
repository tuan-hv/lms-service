package com.smartosc.fintech.lms.common.constant;

import com.smartosc.fintech.lms.common.util.ResourceUtil;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static com.smartosc.fintech.lms.common.constant.ErrorCode.CONTACT_REF_TYPE_ERROR;
import static com.smartosc.fintech.lms.common.constant.MessageKey.CONTACT_REF_TYPE_INVALID;

@Getter
@AllArgsConstructor
public enum ContactRefType {
    SIBLING(1, "Sibling"),
    FRIEND_COLLEAGUES(2, "Friend/ Colleagues"),
    CHILD(3, "Child"),
    RELATIVES(4, "Relatives"),
    SPOUSE(5, "Spouse");

    private int value;
    private String name;

    public static ContactRefType valueOf(int value) {
        return Arrays.stream(values())
                .filter(status -> status.getValue() == value).findFirst()
                .orElseThrow(() -> new BusinessServiceException(ResourceUtil.getMessage(CONTACT_REF_TYPE_INVALID), CONTACT_REF_TYPE_ERROR));
    }
}
