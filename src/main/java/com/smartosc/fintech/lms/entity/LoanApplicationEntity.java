package com.smartosc.fintech.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "loan_application")
public class LoanApplicationEntity extends AuditEntity implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "uuid")
  private String uuid;

  @Column(name = "contract_number")
  private String contractNumber;

  @Column(name = "loan_amount")
  private BigDecimal loanAmount;

  @Column(name = "interest_rate")
  private String interestRate;

  @Column(name = "loan_term")
  private String loanTerm;

  @Column(name = "term_unit")
  private String termUnit;

  @Column(name = "status")
  private Integer status;

  @Column(name = "last_modified_date")
  private Timestamp lastModifiedDate;

  @Column(name = "approve_date")
  private Timestamp approveDate;

  @Column(name = "signed_off_date")
  private Timestamp signedOffDate;

  @Column(name = "closed_date")
  private Timestamp closedDate;

  @Column(name = "application_state")
  private String applicationState;

  @Column(name = "application_sub_state")
  private String applicationSubState;

  @Column(name = "accrued_interest")
  private BigDecimal accruedInterest;

  @Column(name = "accrue_late_interest")
  private BigDecimal accrueLateInterest;

  @Column(name = "fee_due")
  private BigDecimal feeDue;

  @Column(name = "fee_paid")
  private BigDecimal feePaid;

  @Column(name = "fixed_days_of_month")
  private Integer fixedDaysOfMonth;

  @Column(name = "interest_application_method")
  private String interestApplicationMethod;

  @Column(name = "interest_calculation_method")
  private String interestCalculationMethod;

  @Column(name = "interest_due")
  private BigDecimal interestDue;

  @Column(name = "interest_paid")
  private BigDecimal interestPaid;

  @Column(name = "payment_method")
  private String paymentMethod;

  @Column(name = "penalty_due")
  private BigDecimal penaltyDue;

  @Column(name = "penalty_paid")
  private BigDecimal penaltyPaid;

  @Column(name = "principal_due")
  private BigDecimal principalDue;

  @Column(name = "principal_paid")
  private BigDecimal principalPaid;

  @Column(name = "loan_product_key")
  private String loanProductKey;

  @Column(name = "repayment_installments")
  private Integer repaymentInstallments;

  @Column(name = "repayment_period_count")
  private Integer repaymentPeriodCount;

  @Column(name = "repayment_period_unit")
  private String repaymentPeriodUnit;

  @Column(name = "repayment_schedule_method")
  private String repaymentScheduleMethod;

  @Column(name = "schedule_due_dates_method")
  private String scheduleDueDatesMethod;

  @Column(name = "tax_rate")
  private BigDecimal taxRate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", table = "loan_application")
  private UserEntity user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "loan_product_id", referencedColumnName = "id", nullable = false, table = "loan_application")
  private LoanProductEntity loanProduct;

  @OneToMany(mappedBy = "loanApplication",fetch = FetchType.LAZY)
  private Collection<LoanContactInformationEntity> loanContactInformation;

  @OneToMany(mappedBy = "loanApplication",fetch = FetchType.LAZY)
  private Collection<LoanCreditScoreEntity> loanCreditScores;

  @OneToMany(mappedBy = "loanApplication",fetch = FetchType.LAZY)
  private Collection<LoanDisbursementMethodEntity> loanDisbursementMethods;

  @OneToMany(mappedBy = "loanApplication",fetch = FetchType.LAZY)
  private Collection<LoanJobInformationEntity> loanJobInformation;

  @OneToMany(mappedBy = "loanApplication",fetch = FetchType.LAZY)
  private Collection<LoanKycInformationEntity> loanKycInformation;

  @OneToMany(mappedBy = "loanApplication",fetch = FetchType.LAZY)
  private Collection<LoanPersonalInformationEntity> loanPersonalInformation;

  @OneToMany(mappedBy = "loanApplication",fetch = FetchType.LAZY)
  private Collection<LoanTransactionEntity> loanTransactions;

  @OneToMany(mappedBy = "loanApplication",fetch = FetchType.LAZY)
  private Collection<RepaymentEntity> repayments;

  @OneToMany(mappedBy = "loanApplication",fetch = FetchType.LAZY)
  private Collection<LenderInfoEntity> lenderInfoEntities;

  @Override
  public String toString() {
    return "LoanApplicationEntity";
  }
}
