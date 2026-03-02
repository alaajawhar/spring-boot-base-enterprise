package com.amdose.base.devportal.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alaa Jawhar
 */
@UtilityClass
public class DateUtil {

    private static final String APP_DATE_PATTERN = "dd-MM-YYYY : HH:mm:ss";

    public static String format(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(APP_DATE_PATTERN);
        return simpleDateFormat.format(date);
    }

}
