package org.example.userservice.utils;

import java.sql.Date;

public class CommonUtil {
    public static Date stringToDate(String date) {
//        System.out.println("date: " + date);
        if (date == null || date.trim().isEmpty()) {
            System.out.println("Date string is null or empty");
            return null;
        }
        return Date.valueOf(date);
    }
}
