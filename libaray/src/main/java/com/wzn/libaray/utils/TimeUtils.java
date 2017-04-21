package com.wzn.libaray.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * ${DESCRIPTION}
 * <p/>
 * Created by luona on 16/8/4.
 */
public class TimeUtils {
    /**
     * 时间戳格式化
     *
     * @param date
     * @return
     */
    public static String formatDate(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
        return sdf.format(new Date(date));
    }

    /**
     * 时间戳格式化
     *
     * @param date
     * @return
     */
    public static String formatDate1(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return sdf.format(new Date(date));
    }

    /**
     * 时间戳格式化(作为后缀时)
     *
     * @return
     */
    public static String formatDateAsSuffix() {
        SimpleDateFormat sdf = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss", Locale.CHINA);
        return sdf.format(new Date());
    }

    /**
     * 自定义时间戳格式化
     *
     * @param date
     * @param format 时间格式 例：yyyy－MM-dd
     * @return
     */
    public static String formatDate_common(long date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(new Date(date));
    }

    /**
     * 将格式化字符串转化为时间戳
     *
     * @param format
     * @param formateDate
     * @return
     */
    public static long getTimeFromFormatDate(String format, String formateDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
            Date date = sdf.parse(formateDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Calendar getLocaleCalendar() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return calendar;
    }

    /**
     * 计算两个时间点之间差几天
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getDistimeStr(long startTime, long endTime) {
        String str = "--";
        long disLong = endTime - startTime;
        if (disLong > 0) {
            long day = disLong / (24 * 60 * 60 * 1000);
            long yu = disLong % (24 * 60 * 60 * 1000);
            if(yu != 0 )
                day += 1;
            str = day + "天";
        }
        return str;
    }
}
