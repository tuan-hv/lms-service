package com.smartosc.fintech.lms.dto;

import lombok.Data;

@Data
public class LoanPersonalInformationDto {

    private long id;

    private String fullName;

    private String phoneNumber;

    private String dateOfBirth;

    private String emailAddress;

    private String address;

}
