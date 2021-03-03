package com.smartosc.fintech.lms.repository.specification;

import com.smartosc.fintech.lms.common.constant.RepaymentState;
import com.smartosc.fintech.lms.entity.RepaymentEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepaymentSpecification {
    public static final String STATE_FIELD = "state";
    private static final String LOAN_APPLICATION_FIELD = "loanApplication";
    private static final String UUID_FIELD = "uuid";
    public static final String DUE_DATE_FIELD = "dueDate";

    public static Specification<RepaymentEntity> stateNotEqual(RepaymentState state) {
        return (transaction, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(transaction.get(STATE_FIELD), state.name());
    }

    public static Specification<RepaymentEntity> loanApplicationKeyEq(String uuid) {
        if (Strings.isBlank(uuid)) {
            return null;
        }
        return (transaction, query, criteriaBuilder) ->
                criteriaBuilder.equal(transaction.get(LOAN_APPLICATION_FIELD).get(UUID_FIELD), uuid);
    }

    public static Specification<RepaymentEntity> creationDateGte(Long fromDate) {
        if (Objects.isNull(fromDate)) {
            return null;
        }
        return (transaction, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(transaction.get(DUE_DATE_FIELD), new Timestamp(fromDate));
    }

    public static Specification<RepaymentEntity> creationDateLte(Long toDate) {
        if (Objects.isNull(toDate)) {
            return null;
        }
        return (transaction, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(transaction.get(DUE_DATE_FIELD), new Timestamp(toDate));
    }
}
