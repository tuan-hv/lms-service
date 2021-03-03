package com.smartosc.fintech.lms.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageKey {
    public static final String APP_RESPONSE_OK = "app.response.ok";

    public static final String HANDLE_MISSING_SERVLET_REQUEST_PARAMETER = "handle.missing.servlet.request.parameter";
    public static final String HANDLE_HTTP_MEDIA_TYPE_NOT_SUPPORTED = "handle.http.media.type.not.supported";
    public static final String HANDLE_METHOD_ARGUMENT_NOT_VALID = "handle.method.argument.not.valid";
    public static final String HANDLE_CONSTRAINT_VIOLATION = "handle.constraint.violation";
    public static final String HANDLE_HTTP_MESSAGE_NOT_READABLE = "handle.http.message.not.readable";
    public static final String HANDLE_HTTP_MESSAGE_NOT_WRITABLE = "handle.http.message.not.writable";
    public static final String HANDLE_NOT_FOUND_EXCEPTION = "handle.not.found.exception";
    public static final String HANDLE_DATA_INTEGRITY_VIOLATION = "handle.data.integrity.violation";
    public static final String INTERNAL_SERVER_ERROR = "internal.server.error";
    public static final String HANDLE_METHOD_ARGUMENT_TYPE_MISMATCH = "handle.method.argument.type.mismatch";

    public static final String PAYMENT_GATEWAY_TIMEOUT = "payment.gateway.timeout";
    public static final String PAYMENT_GATEWAY_FAIL = "payment.gateway.fail";
    public static final String PAYMENT_FUNDING_MESSAGE = "payment.funding.message";

    public static final String FUNDING_EMAIL_SUBJECT = "funding.email.subject";
    public static final String REPAYMENT_EMAIL_SUBJECT = "repayment.email.subject";
    public static final String EMAIL_SEND_FAIL = "email.send.fail";

    public static final String APPLICATION_NOT_FOUND = "loan.application.not.found";
    public static final String DISBURSEMENT_METHOD_NOT_FOUND = "loan.disbursement.method.not.found";
    public static final String BENEFICIARY_INFO_NOT_FOUND = "beneficiary.info.not.found";
    public static final String LENDER_INFO_NOT_FOUND = "lender.info.not.found";
    public static final String PERSONAL_INFO_NOT_FOUND = "personal.info.not.found";
    public static final String FIN_INFO_NOT_FOUND = "loan.fin.info.not.found";
    public static final String REPAYMENT_NOT_FOUND = "repayment.not.found";
    public static final String TRANSACTION_NOT_FOUND = "loan.transaction.not.found";

    public static final String APPLICATION_MANY_RECORD = "loan.application.many.record";
    public static final String APPLICATION_CLOSED = "loan.application.closed";
    public static final String REPAYMENT_VALIDATE_UUID = "repayment.validate.uuid";
    public static final String REPAYMENT_VALIDATE_STATUS = "repayment.validate.status";
    public static final String REPAYMENT_VALIDATE_AMOUNT = "repayment.validate.amount";
    public static final String FIN_INFO_VALIDATOR_SIMILAR = "loan.fin.info.validator.similar";
    public static final String FIN_INFO_VALIDATOR_INPUT = "loan.fin.info.validator.input";
    public static final String DISBURSEMENT_INVALID_UUID = "disbursement.invalid.uuid";
    public static final String DISBURSEMENT_INVALID_STATUS = "disbursement.invalid.status";
    public static final String TRANSACTION_INPUT_DATE_INVALID = "loan.transaction.input.date.invalid";
    public static final String APPLICATION_STATUS_INVALID = "loan.application.status.invalid";
    public static final String CONTACT_REF_TYPE_INVALID= "loan.contact.info.ref.type.invalid";
    public static final String REPAYMENT_SCHEDULE_INPUT_DATE_INVALID = "repayment.schedule.input.date.invalid";
    public static final String PERIOD_UNIT_INVALID = "period.unit.invalid";
    public static final String CURRENCY_CODE_INVALID = "currency.code.invalid";
    public static final String DISBURSEMENT_METHOD_INVALID = "disbursement.method.invalid";


    public static final String FIN_INFO_VALIDATOR_OVER_ALLOWED_VALUE = "loan.fin.info.validator.over_allowed_value";
    public static final String SEARCH_LOAN_INPUT_DATE_INVALID = "loan.search.input.date.invalid";

}
