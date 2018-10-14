package com.kakaopay.meeting_room.support;

import java.util.Calendar;
import java.util.Date;

public class DateEntity {

    public static Date addDate(Date originDate, int addDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originDate);
        calendar.add(Calendar.DATE, addDate);
        return calendar.getTime();
    }

    public static Date trimTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static long getDiffDate(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / (24*60*60*1000);
    }

    public static int getWeeks(Date startDate, Date endDate) {
        if(endDate == null)
            return 0;
        return (int)getDiffDate(startDate, endDate) / 7;
    }

    public static double changeTimeType(String time) {
        String[] split = time.split(":");
        if(split[1].equals("00"))
            return Integer.parseInt(split[0]);
        return Integer.parseInt(split[0]) + 0.5;
    }

    public static Date getToday() {
        return new Date();
    }
}
