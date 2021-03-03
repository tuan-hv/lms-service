package com.smartosc.fintech.lms.entity;

import lombok.Data;

import javax.persistence.CascadeType;
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
@Table(name = "repayment")
public class RepaymentEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "due_date")
    private Timestamp dueDate;

    @Column(name = "interest_due")
    private BigDecimal interestDue;

    @Column(name = "interest_paid")
    private BigDecimal interestPaid;

    @Column(name = "last_paid_date")
    private Timestamp lastPaidDate;

    @Column(name = "last_penalty_applied_date")
    private Timestamp lastPenaltyAppliedDate;

    @Column(name = "penalty_due")
    private BigDecimal penaltyDue;

    @Column(name = "penalty_paid")
    private BigDecimal penaltyPaid;

    @Column(name = "principal_due")
    private BigDecimal principalDue;

    @Column(name = "principal_paid")
    private BigDecimal principalPaid;

    @Column(name = "fee_due")
    private BigDecimal feeDue;

    @Column(name = "fee_paid")
    private BigDecimal feePaid;

    @Column(name = "repaid_date")
    private Timestamp repaidDate;

    @Column(name = "state")
    private String state;

    @Column(name = "notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_key", referencedColumnName = "uuid", table = "repayment")
    private UserEntity user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "loan_application_key", referencedColumnName = "uuid", table = "repayment")
    private LoanApplicationEntity loanApplication;

    @OneToMany(mappedBy = "repayment", fetch = FetchType.LAZY)
    private Collection<RepaymentFeeDetailsEntity> repaymentFeeDetails;
}
