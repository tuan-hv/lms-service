package com.smartosc.fintech.lms.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.ResourceBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceUtil {
    private static final String RESOURCE_BUNDLE_NAME = "MessagesBundle";

    private static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, Locale.ROOT);
    }

    public static String getMessage(String key) {
        return getResourceBundle().getString(key);
    }
}
