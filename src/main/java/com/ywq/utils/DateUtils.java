package com.ywq.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String HH_MM_SS = "HH:mm:ss";

    public static String getTodayDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        return now.format(dateTimeFormatter);
    }

    public static String getLocalTime(LocalDateTime dateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(HH_MM_SS);
        return dateTime.toLocalTime().format(dateTimeFormatter);
    }
}

