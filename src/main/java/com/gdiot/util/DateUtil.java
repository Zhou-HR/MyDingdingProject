package com.gdiot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ZhouHR
 */
public class DateUtil {
    /**
     * 判定当前时间是否空闲，能否发下行指令
     *
     * @param time
     * @return
     */
    public static boolean ifCurrentTimeFree(long time) {
        boolean isFree = true;
        //毫秒
        long startTime = time + 30 * 60 * 1000;
        long endTime = time + 33 * 60 * 1000;
        long currentTime = System.currentTimeMillis();
        if (currentTime <= endTime && currentTime >= startTime) {
            isFree = false;
        }
        return isFree;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (null == seconds || seconds.isEmpty() || "null".equals(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param millisecond 精确到毫秒的字符串
     * @param format
     * @return
     */
    public static String milliSecond2Date(String millisecond, String format) {
        if (millisecond == null || millisecond.isEmpty() || "null".equals(millisecond)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(millisecond)));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long date2TimeStampLong(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    public static void main(String[] args) {
        String timeStamp = timeStamp();

        //运行输出:timeStamp=1470278082
        System.out.println("timeStamp=" + timeStamp);

        //运行输出:1470278082980
        System.out.println(System.currentTimeMillis());
        //该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数

        String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");

        //运行输出:date=2016-08-04 10:34:42
        System.out.println("date=" + date);

        String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");
        //运行输出:1470278082
        System.out.println(timeStamp2);
    }
}
