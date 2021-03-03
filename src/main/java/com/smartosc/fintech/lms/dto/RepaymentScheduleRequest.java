package com.smartosc.fintech.lms.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepaymentScheduleRequest {
    @NotBlank
    String uuid;
    Long fromDate;
    Long toDate;
    int pageNumber;
    int pageSize = 5;
}
