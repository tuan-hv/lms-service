package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.common.util.DateTimeUtil;
import com.smartosc.fintech.lms.dto.PaymentAmountDto;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.util.Collection;

@Mapper(uses = {BigDecimalMapper.class})
public interface PaymentAmountMapper {

    static PaymentAmountMapper getInstance() {
        return Mappers.getMapper(PaymentAmountMapper.class);
    }

    @Mapping(source = "principalDue", target = "principal", qualifiedByName = "mapToBigDecimalScale")
    @Mapping(source = "interestDue", target = "interest", qualifiedByName = "mapToBigDecimalScale")
    @Mapping(source = "dueDate", target = "dueDate", qualifiedByName = "mapDuedateToString")
    PaymentAmountDto entityToDto(RepaymentEntity repaymentEntity);


    @Named("mapToListPaymentAmount")
    Collection<PaymentAmountDto> mapToListPaymentAmount(Collection<RepaymentEntity> repaymentEntities);

    @Named("mapDuedateToString")
    static String mapBirthdayToString(Timestamp date) {
        return DateTimeUtil.getFormatTimestamp(date);
    }
}
