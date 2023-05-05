package com.example.demo5.util;


import com.example.demo5.entity.Group;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 按照区间范围动态分组
 * @author huyue87
 * @date 2020-11-06 14:39 2020-11-06 14:49
 * 用于驻留点统计的方法
 */
public class StatisticUtils {

    /**
     * 属性查询工具类
     * @param duration 停留时长 duration 不用除60
     * @return 统计值
     */
    public static List<Group> durationStat(List<Double> duration) {
        /**
         * 构造数组
         */
        List<Group> res = GroupUtils.durationGroup();
        Map<String, Double> tmp = NumUtils.computePro1(duration
                .parallelStream()
                .collect(Collectors.groupingBy(
                        num->{
                            if(num < 10.0) {return "[0,10)";}
                            if(num < 20.0) {return "[10,20)";}
                            if(num < 30.0) {return "[20,30)";}
                            if(num < 40.0) {return "[30,40)";}
                            if(num < 50.0) {return "[40,50)";}
                            if(num < 60.0) {return "[50,60)";}
                            else {return "[60, )";}
                        },  TreeMap::new, Collectors.counting())), duration.size());
        return getGroupValue(res, tmp);
    }

    /**
     * 访问星期分布
     */
    public static List<Group> weekDayStat(List<String> weekDay) {
        List<Group> res = GroupUtils.weekDayGroup();
        Map<String, Double> tmp = NumUtils.computePro1(weekDay
                .parallelStream()
                .collect(Collectors.groupingBy(
                        week->{
                            if(week.equals("1")) {return "Mon";}
                            if(week.equals("2")) {return "Tue";}
                            if(week.equals("3")) {return "Wed";}
                            if(week.equals("4")) {return "Thur";}
                            if(week.equals("5")) {return "Fri";}
                            if(week.equals("6")) {return "Sat";}
                            else {return "Sun";}
                        },  TreeMap::new, Collectors.counting())), weekDay.size());
        return getGroupValue(res, tmp);
    }

    /**
     * 时间段分组
     */
    public static List<Group> timeSegStat(List<Date> midTime) {
        List<Group> res = GroupUtils.timeSegGroup();
        Map<String, Double> tmp = NumUtils.computePro1(midTime
                .parallelStream()
                .map(DateUtils::getHour)
                .collect(Collectors.groupingBy(
                        hour->{
                            if(hour < 3) {return "0-3";}
                            else if(hour < 6) {return "3-6";}
                            else if(hour < 9) {return "6-9";}
                            else if(hour < 12) {return "9-12";}
                            else if(hour < 15) {return "12-15";}
                            else if(hour < 18) {return "15-18";}
                            else if(hour < 21) {return "18-21";}
                            else {return "21-24";}
                        },  TreeMap::new, Collectors.counting())), midTime.size());
        return getGroupValue(res, tmp);
    }

    public static List<Map<String, Object>> yearStat(List<Date> midTime) {
        int sumCount = midTime.size();
        return midTime.parallelStream()
                .collect(Collectors.groupingBy(Date::getYear ,TreeMap::new, Collectors.toList()))
                .entrySet()
                .parallelStream()
                .map(
                        e->{
                            Map<String, Object> item = new TreeMap<>();
                            double prop = (double) e.getValue().size() / sumCount;
                            item.put("name", e.getKey() + 1900);
                            item.put("value", prop);
                            return item;
                        }
                ).collect(Collectors.toList());
    }

    public static List<Map<String, Object>> monthStat(List<Date> midTime) {
        int sumCount = midTime.size();
        return midTime.parallelStream()
                .collect(Collectors.groupingBy(Date::getMonth ,TreeMap::new, Collectors.toList()))
                .entrySet()
                .parallelStream()
                .map(
                        e->{
                            Map<String, Object> item = new TreeMap<>();
                            double prop = (double) e.getValue().size() / sumCount;
                            item.put("name", e.getKey() + 1);
                            item.put("value", prop);
                            return item;
                        }
                ).collect(Collectors.toList());
    }



    /**
     * 时间段分组
     */
    public static List<Group> timeSegStatPlace(List<String> timeSegment) {
        List<Group> res = GroupUtils.timeSegGroup();
        Map<String, Double> tmp = NumUtils.computePro1(timeSegment
                .parallelStream()
                .collect(Collectors.groupingBy(
                        hour->{
                            return hour;
                        },  TreeMap::new, Collectors.counting())), timeSegment.size());
        return getGroupValue(res, tmp);
    }



    public static List<Group> intersectStat(List<Integer> intersect) {
        List<Group> res = GroupUtils.intersectGroup();
        Map<String, Double> tmp = NumUtils.computePro1(intersect
                .parallelStream()
                .collect(Collectors.groupingBy(
                        num->{
                            if(num < 3) {return "[0,3)";}
                            else if(num < 6) {return "[3,6)";}
                            else if(num < 10) {return "[6,10)";}
                            else {return "[10, ]";}
                        },  TreeMap::new, Collectors.counting())), intersect.size());
        return getGroupValue(res, tmp);
    }

    public static List<Group> roadStat(List<Double> roadLen) {
        List<Group> res = GroupUtils.roadGroup();
        Map<String, Double> tmp = NumUtils.computePro1(roadLen
                .parallelStream()
                .collect(Collectors.groupingBy(
                        num->{
                            if(num < 100.0) {return "[0,100)";}
                            else if(num < 500.0) {return "[100,500)";}
                            else if(num < 1000.0) {return "[500,1000)";}
                            else {return "[1000, )";}
                        },  TreeMap::new, Collectors.counting())), roadLen.size());
        return getGroupValue(res, tmp);
    }

    /**
     * 访问频次分布
     */
    public static List<Group> freqStat(List<Double> freq) {
        List<Group> res = GroupUtils.freqGroup();
        Map<String, Double> tmp = NumUtils.computePro1(freq
                .parallelStream()
                .collect(Collectors.groupingBy(
                        num->{
                            if(num < 0.5) {return "[0,0.5)";}
                            else if(num < 1.0) {return "[0.5,1)";}
                            else if(num < 1.5) {return "[1.0,1.5)";}
                            else if(num < 2.0) {return "[1.5,2.0)";}
                            else if(num < 2.5) {return "[2.0,2.5)";}
                            else if(num < 3.0) {return "[2.5,3.0)";}
                            else {return "[3, )";}
                        },  TreeMap::new, Collectors.counting())), freq.size());
        return getGroupValue(res, tmp);
    }


    /**
     * 访问次数分布(包括次数和对象)
     */
    public static List<Group> visitStat(List<Integer> visit) {
        List<Group> res = GroupUtils.visitGroup();
        Map<String, Double> tmp = NumUtils.computePro1(visit
                .parallelStream()
                .collect(Collectors.groupingBy(
                        num->{
                            if(num < 20) {return "[0,20)";}
                            else if(num < 50) {return "[20,50)";}
                            else if(num < 100) {return "[50,100)";}
                            else if(num < 200) {return "[100,200)";}
                            else if(num < 500) {return "[200,500)";}
                            else if(num < 1000) {return "[500,1000)";}
                            else {return "[1000, )";}
                        },  TreeMap::new, Collectors.counting())), visit.size());
        return getGroupValue(res, tmp);
    }



    public static List<Group> getGroupValue(List<Group> groups,Map<String, Double> pairs) {
        for(Group group : groups) {
            if(pairs.get(group.getName()) != null) {
                group.setValue(pairs.get(group.getName()));
            }
        }
        return groups;
    }
}
