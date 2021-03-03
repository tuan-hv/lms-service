package com.smartosc.fintech.lms.dto;

import lombok.Data;


@Data
public class LoanContactInformationDto {

    private long id;

    private Integer refType;

    private String refTypeName;

    private String refFullName;

    private String refPhoneNumber;

    private String refDob;

    private String refEmail;

    private String refAddress;
}
