package com.smartosc.fintech.lms.common.util;

import com.smartosc.fintech.lms.common.constant.CurrencyCode;
import com.smartosc.fintech.lms.common.constant.ErrorCode;
import com.smartosc.fintech.lms.exception.BusinessServiceException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.smartosc.fintech.lms.common.constant.MessageKey.CURRENCY_CODE_INVALID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyUtil {
    public static int getMinorUnit(CurrencyCode code) {
        switch (code) {
            case VND: return 0;
            case USD: return 2;
            default:
                throw new BusinessServiceException(ResourceUtil.getMessage(CURRENCY_CODE_INVALID), ErrorCode.CURRENCY_CODE_ERROR);
        }
    }
}
