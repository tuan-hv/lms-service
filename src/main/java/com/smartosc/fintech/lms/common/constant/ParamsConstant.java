package com.smartosc.fintech.lms.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParamsConstant {
    public static final int LEAD_DAY = 3;
    public static final BigDecimal DAY_OF_YEAR = BigDecimal.valueOf(365);

    public static final long ALLOWED_MAX_NUMBER = 999999999999999l;

    public static final int ROUNDING_SCALE = 0;
    public static final int SCALE = 4;
    public static final BigDecimal ZERO_2SCALE = BigDecimal.ZERO.setScale(2, RoundingMode.CEILING);



}
