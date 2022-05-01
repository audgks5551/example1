package com.wiken.example1.base.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static String getCompareCurrentAndPastDates(LocalDateTime createdDate) {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        long YEARS = ChronoUnit.YEARS.between(createdDate, currentLocalDateTime);
        long MONTHS = ChronoUnit.MONTHS.between(createdDate, currentLocalDateTime);
        long WEEKS = ChronoUnit.WEEKS.between(createdDate, currentLocalDateTime);
        long DAYS = ChronoUnit.DAYS.between(createdDate, currentLocalDateTime);
        long HOURS = ChronoUnit.HOURS.between(createdDate, currentLocalDateTime);
        long SECONDS = ChronoUnit.SECONDS.between(createdDate, currentLocalDateTime);

        if (YEARS != 0) {
            return String.format("%d년 전", YEARS);
        } else if (MONTHS != 0) {
            return String.format("%d달 전", MONTHS);
        } else if (WEEKS != 0) {
            return String.format("%d주 전", WEEKS);
        } else if (DAYS != 0) {
            return String.format("%d일 전", DAYS);
        } else if (HOURS != 0) {
            return String.format("%d시간 전", HOURS);
        } else if (SECONDS/60 != 0) {
            return String.format("%d분 전", SECONDS / 60);
        } else {
            return "방금 전";
        }
    }

    public static String getOrderlyDate(LocalDateTime createdDate) {
        int year = createdDate.getYear();
        int month = createdDate.getMonthValue();
        int day = createdDate.getDayOfMonth();

        return String.format("%d년 %d월 %d일", year, month, day);
    }
}
