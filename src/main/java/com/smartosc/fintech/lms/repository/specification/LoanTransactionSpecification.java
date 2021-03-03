package com.smartosc.fintech.lms.repository.specification;

import com.smartosc.fintech.lms.entity.LoanTransactionEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoanTransactionSpecification {
    private static final String LOAN_APPLICATION_FIELD = "loanApplication";
    private static final String UUID_FIELD = "uuid";
    private static final String CREATION_DATE_FIELD = "creationDate";

    public static Specification<LoanTransactionEntity> loanApplicationKeyEq(String uuid) {
        if (Strings.isBlank(uuid)) {
            return null;
        }
        return (transaction, query, criteriaBuilder) ->
                criteriaBuilder.equal(transaction.get(LOAN_APPLICATION_FIELD).get(UUID_FIELD), uuid);
    }

    public static Specification<LoanTransactionEntity> creationDateGte(Long fromDate) {
        if (Objects.isNull(fromDate)) {
            return null;
        }
        return (transaction, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(transaction.get(CREATION_DATE_FIELD), new Timestamp(fromDate));
    }

    public static Specification<LoanTransactionEntity> creationDateLte(Long toDate) {
        if (Objects.isNull(toDate)) {
            return null;
        }
        return (transaction, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(transaction.get(CREATION_DATE_FIELD), new Timestamp(toDate));
    }
}
