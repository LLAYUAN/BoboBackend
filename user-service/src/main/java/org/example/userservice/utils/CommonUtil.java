package org.example.userservice.utils;

import java.sql.Date;

public class CommonUtil {
    public static Date stringToDate(String date) {
        if(date == null)
            return null;
        return Date.valueOf(date);
    }
}
