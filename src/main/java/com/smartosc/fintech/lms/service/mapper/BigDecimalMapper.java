package com.smartosc.fintech.lms.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.smartosc.fintech.lms.common.constant.ParamsConstant.SCALE;

@Mapper
public interface BigDecimalMapper {


    @Named("mapToBigDecimalScale")
    static BigDecimal mapToScale(BigDecimal bigDecimal) {
        return bigDecimal.setScale(SCALE, RoundingMode.CEILING);
    }

    @Named("mapStringToBigDecimalScale")
    static BigDecimal mapStringToBigDecimalScale(String interestRate) {
        BigDecimal bigDecimal = new BigDecimal(interestRate);
        return bigDecimal.setScale(SCALE, RoundingMode.CEILING);
    }
}
