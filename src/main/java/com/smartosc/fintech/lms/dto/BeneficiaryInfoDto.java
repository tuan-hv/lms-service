package com.smartosc.fintech.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryInfoDto implements Serializable {
    private int id;
    private String beneficiaryBank;
    private String beneficiaryAccount;
    private String beneficiaryName;
    private Long loanDisbursementId;
}
