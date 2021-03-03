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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
@Data
@Entity
@Table(name = "loan_transaction")
public class LoanTransactionEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "entry_date")
    private Timestamp entryDate;

    @Column(name = "fees_amount")
    private BigDecimal feesAmount;

    @Column(name = "interest_amount")
    private BigDecimal interestAmount;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "penalty_amount")
    private BigDecimal penaltyAmount;

    @Column(name = "principal_amount")
    private BigDecimal principalAmount;

    @Column(name = "principal_balance")
    private BigDecimal principalBalance;

    @Column(name = "tax_on_fees_amount")
    private BigDecimal taxOnFeesAmount;

    @Column(name = "tax_on_interest_amount")
    private BigDecimal taxOnInterestAmount;

    @Column(name = "tax_on_penalty_amount")
    private BigDecimal taxOnPenaltyAmount;

    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key", referencedColumnName = "uuid", table = "loan_transaction")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_application_key", referencedColumnName = "uuid", table = "loan_transaction")
    private LoanApplicationEntity loanApplication;

    @OneToMany(mappedBy = "loanTransaction", fetch = FetchType.LAZY)
    private Collection<RepaymentFeeDetailsEntity> repaymentFeeDetails;

}
