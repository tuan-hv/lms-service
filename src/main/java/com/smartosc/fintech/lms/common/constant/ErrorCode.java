package com.smartosc.fintech.lms.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCode {
    // Define business exception code from 1000
    public static final int PAYMENT_GATEWAY_FAIL = 1001;
    public static final int PAYMENT_GATEWAY_TIMEOUT = 1002;
    public static final int LOAN_APPLICATION_CLOSE_ALREADY = 1003;
    public static final int REPAYMENT_AMOUNT_EMPTY = 1004;
    public static final int REPAYMENT_UUID_EMPTY = 1005;
    public static final int REPAYMENT_RESULT_EMPTY = 1006;
    public static final int SEND_EMAIL_FAIL = 1007;
    public static final int NOT_ALLOWED = 1008;
    public static final int EXPECTONE_GETTOOMANY = 1009;
    public static final int EMPTY_RESULT = 1010;
    public static final int DISBURSEMENT_UUID_ERROR = 1011;
    public static final int DISBURSEMENT_STATUS_ERROR = 1012;
    public static final int TRANSACTION_INPUT_DATE_ERROR = 1013;
    public static final int REPAYMENT_SCHEDULE_INPUT_DATE_ERROR = 1014;
    public static final int SEARCH_LOAN_INPUT_DATE_ERROR = 1015;
    public static final int PERIOD_UNIT_ERROR = 1016;
    public static final int CURRENCY_CODE_ERROR = 1017;
    public static final int APPLICATION_STATUS_ERROR = 1018;
    public static final int CONTACT_REF_TYPE_ERROR = 1019;
    public static final int DISBURSEMENT_METHOD_STATUS_ERROR = 1020;

}
