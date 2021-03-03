package com.smartosc.fintech.lms.dto;

import lombok.Data;

@Data
public class LoanJobInformationDto {
    private long id;

    private Long preTaxIncome;

    private String companyName;

    private String companyAddress;

    private String latestUpdate;

    private Long expense;
}
