package com.smartosc.fintech.lms.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchLoanDto {

    private Integer status;

    @Size(max = 100)
    private String loanType;

    @Size(max = 20)
    private String loanAccount;

    @Size(max = 250)
    private String customerName;

    @Size(max = 50)
    private String cardNumber;

    private Long createFrom;

    private Long createTo;

    private Integer pageNumber=0;

    private Integer pageSize=10;
}
