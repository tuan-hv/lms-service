package com.smartosc.fintech.lms.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
@Data
@Entity
@Table(name = "loan_product")
public class LoanProductEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "min_amount")
    private BigDecimal minAmount;

    @Column(name = "max_amount")
    private BigDecimal maxAmount;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "type")
    private Integer type;

    @Column(name = "interest_accrued_accounting_method")
    private String interestAccruedAccountingMethod;

    @Column(name = "interest_application_method")
    private String interestApplicationMethod;

    @Column(name = "interest_calculation_method")
    private String interestCalculationMethod;

    @Column(name = "interest_rate_key")
    private String interestRateKey;

    @Column(name = "fixed_days_of_month")
    private Integer fixedDaysOfMonth;

    @Column(name = "penalty_calculation_method")
    private String penaltyCalculationMethod;

    @Column(name = "paymentmethod")
    private String paymentmethod;

    @Column(name = "prepayment_acceptance")
    private String prepaymentAcceptance;

    @Column(name = "prepayment_recalculation_method")
    private String prepaymentRecalculationMethod;

    @Column(name = "principal_paid_installment_status")
    private String principalPaidInstallmentStatus;

    @Column(name = "principal_payment_key")
    private String principalPaymentKey;

    @Column(name = "repayment_allocation_order")
    private String repaymentAllocationOrder;

    @Column(name = "repayment_period_count")
    private Integer repaymentPeriodCount;

    @Column(name = "repayment_period_unit")
    private String repaymentPeriodUnit;

    @Column(name = "repayment_schedule_method")
    private String repaymentScheduleMethod;

    @Column(name = "tax_calculation_method")
    private String taxCalculationMethod;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "last_updated_date")
    private Timestamp lastUpdatedDate;

    @Column(name = "days_in_year")
    private Integer daysInYear;

    @Column(name = "loan_penalty_rate")
    private BigDecimal loanPenaltyRate;

    @OneToMany(mappedBy = "loanProduct", fetch = FetchType.LAZY)
    private Collection<LoanApplicationEntity> loanApplications;
}
