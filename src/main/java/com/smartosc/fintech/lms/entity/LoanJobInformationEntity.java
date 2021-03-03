package com.smartosc.fintech.lms.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loan_job_information")
public class LoanJobInformationEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "pre_tax_income")
    private BigDecimal preTaxIncome;

    @Column(name = "expense")
    private BigDecimal expense;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "last_update")
    private Date lastUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_application_id", referencedColumnName = "id", nullable = false, table = "loan_job_information")
    private LoanApplicationEntity loanApplication;
}
