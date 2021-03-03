package com.smartosc.fintech.lms.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanTermPeriod {
    DAYS(1, "days"),
    WEEKS(2, "weeks"),
    MONTHS(3, "months"),
    YEARS(4, "years");

    private final int value;
    private final String name;
}
