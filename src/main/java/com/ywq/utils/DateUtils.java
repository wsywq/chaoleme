package com.ywq.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static String getTodayDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        return now.format(dateTimeFormatter);
    }
}

