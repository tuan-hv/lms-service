package com.smartosc.fintech.lms.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@Entity
@Table(name = "loan_disbursement_method")
public class LoanDisbursementMethodEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "disbursement_method")
    private Integer disbursementMethod;

    @Column(name = "disbursement_code")
    private String disbursementCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_application_id", referencedColumnName = "id", nullable = false, table = "loan_disbursement_method")
    private LoanApplicationEntity loanApplication;
}
