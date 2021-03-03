package com.smartosc.fintech.lms.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoanFinInfoRequest {

    @Size(max = 15, min = 1)
    @NotBlank
    private String preTaxIncome;

    @Size(max = 150)
    private String companyName;

    @Size(max = 500)
    private String companyAddress;

    @Size(max = 15, min = 1)
    @NotBlank
    private String expense;

}
