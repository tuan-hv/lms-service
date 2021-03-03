package com.smartosc.fintech.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LoanApplicationDto {

  private long id;

  private String uuid;

  private String accountNumber;

  private BigDecimal loanAmount;

  private BigDecimal outstandingBalance;

  private String currency = "VND";

  private BigDecimal interestAccrued;

  private BigDecimal interestDue;

  private BigDecimal interestRate;

  private String loanType;

  private String loanTerm;

  private String termUnit;

  private Integer paymentFrequency;

  private Integer loanStatus;

  private String loanStatusName;

  private String createdDate;

  private String disburseDate;

  private String signedOffDate;

  private String expireDate;

  private Collection<LoanContactInformationDto> loanContactInformation;

  private LoanJobInformationDto loanJobInformation;

  private Collection<LoanPersonalInformationDto> loanPersonalInformation;

  private PaymentAmountDto paymentAmount;

  private DisbursementInfoDto disbursedInfo;
}
