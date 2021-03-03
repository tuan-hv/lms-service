package com.smartosc.fintech.lms.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentGatewayConstants {
    public static final String EQUAL = "=";
    public static final String AND = "&";
    public static final String PARAM_START = "?";
    public static final String AMOUNT_PARAM = "amount";
    public static final String UUID_PARAM = "uuid";
    public static final String PHONE_PARAM = "phone";
    public static final String BENEFICIARY_BANK_PARAM = "beneficiary_bank";
    public static final String BENEFICIARY_ACCOUNT_PARAM = "beneficiary_account";
    public static final String BENEFICIARY_NAME_PARAM = "beneficiary_name";
    public static final String EMAIL_PARAM = "email";
    public static final String RETURN_URL_PARAM = "return_url";
}
