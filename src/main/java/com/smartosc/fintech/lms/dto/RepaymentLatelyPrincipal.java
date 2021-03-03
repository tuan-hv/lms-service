package com.smartosc.fintech.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import static com.smartosc.fintech.lms.common.constant.ParamsConstant.DAY_OF_YEAR;
import static com.smartosc.fintech.lms.common.constant.ParamsConstant.ZERO_2SCALE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentLatelyPrincipal extends BaseRepaymentDto {
    private BigDecimal paidPrincipalLateRate;

    @Override
    public boolean checkExpireDueDate() {
        LocalDateTime current = LocalDateTime.now();
        return current.isAfter(getDueDate());
    }

    @Override
    public boolean checkInPeriod() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime dueDate = getDueDate();
        return (currentDateTime.isAfter(getPreDueDate()) && (currentDateTime.isEqual(dueDate) || currentDateTime.isBefore(dueDate)));
    }

    @Override
    public BigDecimal penaltyPaidPricipalLately() {
        if (!checkExpireDueDate()) {
            return super.penaltyPaidPricipalLately();
        }
        LocalDate dueDate = getDueDate().toLocalDate();
        LocalDate current = LocalDate.now();
        long numberExpiredDay = Period.between(dueDate, current).getDays() + 1L;
        return leftPrincipal()
                .multiply(paidPrincipalLateRate == null ? ZERO_2SCALE : paidPrincipalLateRate)
                .multiply(new BigDecimal(numberExpiredDay))
                .divide(DAY_OF_YEAR);
    }
}
