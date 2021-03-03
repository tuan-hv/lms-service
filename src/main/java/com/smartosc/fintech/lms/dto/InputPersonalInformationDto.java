package com.smartosc.fintech.lms.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class InputPersonalInformationDto {

    @Email(regexp = "^[\\w-\\.!#$%&'*+\\-\\/=?^_`{|}~]+@([\\w-]*[A-Za-z]+[\\w-]*\\.)+[A-Za-z]+$")
    @Size(max = 64)
    private String emailAddress;

    @Size(max = 100)
    @NotBlank
    private String address;
}
