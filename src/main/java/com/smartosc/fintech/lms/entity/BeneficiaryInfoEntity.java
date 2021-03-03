package com.smartosc.fintech.lms.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "beneficiary_info")
public class BeneficiaryInfoEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "beneficiary_bank")
    private String beneficiaryBank;

    @Column(name = "beneficiary_account")
    private String beneficiaryAccount;

    @Column(name = "beneficiary_name")
    private String beneficiaryName;

    @Column(name = "loan_disbursement_id")
    private Long loanDisbursementId;
}
