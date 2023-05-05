package com.example.demo5.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-15 21:49
 * 处理日期的工具
 */
public class DateUtils {

    /**
     * 日期的比较
     *
     * @param first,second 两个日期
     * @return int
     * @since 2.0.0
     */
    public static int compareDate(Date first, Date second) {
        return first.compareTo(second);
    }
    /**
     * 判断是否为工作日
     * @param date 日期
     * @return 是否为工作日
     */
    public static boolean isWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        // 是周末
        if(week == Calendar.SUNDAY || week == Calendar.SATURDAY) {
            return false;
        }
        // 是工作日
        return true;
    }

    /**
     * 返回小时数
     * @param date 日期
     * @return 时间戳
     */
    public static int getHour(Date date) {
        Calendar cal =Calendar.getInstance();
        cal.setTime(date);
        int hourTemp = cal.get(Calendar.HOUR_OF_DAY);
        return hourTemp;
    }

    /**
     * 按照星期几进行
     * @param date 日期
     * @return 周几
     */
    public static String getWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        boolean isFirstSunday = (cal.getFirstDayOfWeek() == Calendar.SUNDAY);
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        if(isFirstSunday){
            weekDay = weekDay - 1;
            if(weekDay == 0){
                weekDay = 7;
            }
        }
//        String[] week = {"Monday", "Tuesday", "Wednesday", "ThursDay", "Friday", "Saturday", "Sunday"};
//        return week[weekDay - 1];
        return Integer.toString(weekDay);
    }

    /**
     * 日期转换为字符串
     * @param date
     * @return
     */
    public static String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    /**
     * 字符串解析为日期
     *
     */
    public static Date string2Date(String s){
        Date res = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            res = sdf.parse(s);
        } catch (Exception e) {
            System.out.println("日期不合法");
        }
        return res;
    }
    /**
     * 计算两个日期相减的天数
     */
    public static Double timeDiff(Date d1, Date d2) {
        return (d1.getTime() - d2.getTime())/(1000.0 * 3600 * 24);
    }
}
