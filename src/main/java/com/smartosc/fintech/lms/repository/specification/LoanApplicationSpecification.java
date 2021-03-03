package com.smartosc.fintech.lms.repository.specification;


import com.smartosc.fintech.lms.entity.LoanApplicationEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoanApplicationSpecification {

    private static final String LOAN_STATUS_FIELD = "status";
    private static final String LOAN_PRODUCT_FIELD = "loanProduct";
    private static final String LOAN_PRODUCT_NAME_FIELD = "name";
    private static final String LOAN_CONTRACT_NUMBER_FIELD = "contractNumber";
    private static final String LOAN_PERSONAL_INFO_FIELD = "loanPersonalInformation";
    private static final String LOAN_PERSONAL_FULLNAME_FIELD = "fullName";
    private static final String LOAN_PERSONAL_IDENTIFICATION_CARD_NUMBER_FIELD = "identificationCardNumber";
    private static final String LOAN_CREATED_DATE_FIELD = "createdDate";
    private static final String LOAN_SIGNED_OFF_DATE_FIELD = "signedOffDate";

    public static Specification<LoanApplicationEntity> whereStatusEqual(Integer status) {
        if (Objects.isNull(status)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(LOAN_STATUS_FIELD), status);
    }

    public static Specification<LoanApplicationEntity> whereLoanTypeEqual(String loanType) {
        if (Strings.isBlank(loanType)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(LOAN_PRODUCT_FIELD).get(LOAN_PRODUCT_NAME_FIELD), loanType);
    }

    public static Specification<LoanApplicationEntity> whereAccountNumberEqual(String contructNumber) {
        if (Strings.isBlank(contructNumber)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(LOAN_CONTRACT_NUMBER_FIELD), like(contructNumber));
    }

    public static Specification<LoanApplicationEntity> whereCustomerNameEqual(String customerName) {
        if (Strings.isBlank(customerName)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.join(LOAN_PERSONAL_INFO_FIELD).get(LOAN_PERSONAL_FULLNAME_FIELD), like(customerName));
    }

    public static Specification<LoanApplicationEntity> whereCustomerCardEqual(String customerCard) {
        if (Strings.isBlank(customerCard)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.join(LOAN_PERSONAL_INFO_FIELD).get(LOAN_PERSONAL_IDENTIFICATION_CARD_NUMBER_FIELD), like(customerCard));
    }

    public static Specification<LoanApplicationEntity> whereCreateDateGreaterThanOrEqualTo(Long createFrom) {
        if (Objects.isNull(createFrom)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(LOAN_CREATED_DATE_FIELD), new Timestamp(createFrom));
    }

    public static Specification<LoanApplicationEntity> whereCreateDateLessThanOrEqualTo(Long createTo) {
        if (Objects.isNull(createTo)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(LOAN_CREATED_DATE_FIELD), new Timestamp(createTo));
    }

    public static Specification<LoanApplicationEntity> whereSignedOffDateGreaterThanOrEqualTo(Long signedOffFrom) {
        if (Objects.isNull(signedOffFrom)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(LOAN_SIGNED_OFF_DATE_FIELD), new Timestamp(signedOffFrom));
    }

    public static Specification<LoanApplicationEntity> whereSignedOffDateLessThanOrEqualTo(Long signedOffTo) {
        if (Objects.isNull(signedOffTo)) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(LOAN_SIGNED_OFF_DATE_FIELD), new Timestamp(signedOffTo));
    }

    private static String like(String s) {
        return "%" + s + "%";
    }

}
