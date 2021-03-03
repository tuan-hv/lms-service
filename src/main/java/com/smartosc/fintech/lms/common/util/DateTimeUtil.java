package com.smartosc.fintech.lms.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtil {
    private static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static String formatDate(Date date) {
        if (date != null) {
            DateFormat format = new SimpleDateFormat(FORMAT_DATE);
            return format.format(date);
        }
        return "";
    }

    public static String getFormatTimestamp(Timestamp date) {
        if (date != null) {
            DateFormat format = new SimpleDateFormat(FORMAT_TIMESTAMP);
            return format.format(date);
        }
        return "";
    }

    public static String date2string(Date date, boolean onlyDate) {
        if (date != null) {
            DateFormat format = null;
            if (onlyDate) {
                format = new SimpleDateFormat(FORMAT_DATE);
            } else {
                format = new SimpleDateFormat(FORMAT_TIMESTAMP);
            }
            return format.format(date);
        }
        return "";
    }

}
