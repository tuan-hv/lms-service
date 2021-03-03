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
import java.sql.Date;

@Data
@Entity
@Table(name = "loan_contact_information")
public class LoanContactInformationEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ref_type")
    private Integer refType;

    @Column(name = "ref_full_name")
    private String refFullName;

    @Column(name = "ref_phone_number")
    private String refPhoneNumber;

    @Column(name = "ref_dob")
    private Date refDob;

    @Column(name = "ref_email")
    private String refEmail;

    @Column(name = "ref_address")
    private String refAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_application_id", referencedColumnName = "id", nullable = false, table = "loan_contact_information")
    private LoanApplicationEntity loanApplication;
}
