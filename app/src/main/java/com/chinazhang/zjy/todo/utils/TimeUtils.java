package com.chinazhang.zjy.todo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    private static final String PATTERN = "yyyy-MM-dd";

    public static String long2string(String pattern, long time) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(time);
    }

    public static String data2string(String pattern, Date date) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    public static long string2long(String pattern, String date) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long date2long(String pattern, Date date) {
        return string2long(pattern, data2string(pattern, date));
    }

    public static Date string2date(String pattern, String time) {
        return long2date(string2long(pattern, time));
    }

    public static Date long2date(long time) {
        return new Date(time);
    }

    public static String long2string(long time) {
        return long2string(PATTERN, time);
    }

    public static String data2string(Date date) {
        return data2string(PATTERN, date);
    }

    public static long string2long(String date) {
        return string2long(PATTERN, date);
    }

    public static long date2long(Date date) {
        return date2long(PATTERN, date);
    }

    public static Date string2date(String time) {
        return string2date(PATTERN, time);
    }

}
