package com.smartosc.fintech.lms.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BeneficiaryInfoRequest {
    @NotBlank
    private String uuid;
}
