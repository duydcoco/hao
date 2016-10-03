package com.hao.app.web.rest.util;

/**
 * @author dongh38@ziroom
 * @Date 16/10/3
 * @Time 下午3:16
 */
public class Asserts {

    private Asserts() {}

    private static void throwIllegalArgumentException(Object errorMessage) {
        throw errorMessage == null ? new IllegalArgumentException() : new IllegalArgumentException(String.valueOf(errorMessage));
    }

    public static String checkNotEmpty(String reference,Object errorMessage) {
        if (reference == null || reference.length() == 0) {
            throwIllegalArgumentException(errorMessage);
        }
        return reference;
    }

    public static String checkNotEmpty(String reference) {
        return checkNotEmpty(reference,null);
    }

    public static String checkNotBlank(String reference,Object errorMessage) {
        if (reference == null || reference.trim().length() == 0) {
            throwIllegalArgumentException(errorMessage);
        }
        return reference;
    }

    public static String checkNotBlank(String reference) {
        return checkNotBlank(reference,null);
    }

}
