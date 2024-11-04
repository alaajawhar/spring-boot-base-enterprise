package com.amdose.common.utils;

import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Alaa Jawhar
 */
@UtilityClass
public class DateUtils {

    private static final String APPLICATION_DATE_FORMAT = "dd-MM-yyyy";

    public static String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat(APPLICATION_DATE_FORMAT);
        return dateFormat.format(date);
    }
}
