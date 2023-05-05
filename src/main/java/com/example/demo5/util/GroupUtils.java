package com.example.demo5.util;

import com.example.demo5.entity.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于分组指标
 *
 * @author huyue87@jd.com
 * @date 2020/12/9 17:22
 * @since 2.0.0
 */
public class GroupUtils {
    /**
     * duration分组
     * @return
     */
    public static List<Group> durationGroup() {
        List<Group> res = new ArrayList<>();
        res.add(new Group("[0,10)",0.0));
        res.add(new Group("[10,20)", 0.0));
        res.add(new Group("[20,30)", 0.0));
        res.add(new Group("[30,40)",0.0));
        res.add(new Group("[40,50)",0.0));
        res.add(new Group("[50,60)",0.0));
        res.add(new Group("[60, )",0.0));
        return res;
    }

    public static List<Group> weekDayGroup() {
        List<Group> res = new ArrayList<>();
        res.add(new Group("Mon",0.0));
        res.add(new Group("Tue", 0.0));
        res.add(new Group("Wed", 0.0));
        res.add(new Group("Thur",0.0));
        res.add(new Group("Fri",0.0));
        res.add(new Group("Sat",0.0));
        res.add(new Group("Sun",0.0));
        return res;
    }

    public static List<Group> timeSegGroup() {
        List<Group> res = new ArrayList<>();
        res.add(new Group("0-3",0.0));
        res.add(new Group("3-6", 0.0));
        res.add(new Group("6-9", 0.0));
        res.add(new Group("9-12",0.0));
        res.add(new Group("12-15",0.0));
        res.add(new Group("15-18",0.0));
        res.add(new Group("18-21",0.0));
        res.add(new Group("21-24",0.0));
        return res;
    }

    public static List<Group> intersectGroup() {
        List<Group> res = new ArrayList<>();
        res.add(new Group("[0,3)",0.0));
        res.add(new Group("[3,6)", 0.0));
        res.add(new Group("[6,10)", 0.0));
        res.add(new Group("[10, ]",0.0));
        return res;
    }

    public static List<Group> roadGroup() {
        List<Group> res = new ArrayList<>();
        res.add(new Group("[0,100)",0.0));
        res.add(new Group("[100,500)", 0.0));
        res.add(new Group("[500,1000)", 0.0));
        res.add(new Group("[1000, )",0.0));
        return res;
    }

    public static List<Group> freqGroup() {
        List<Group> res = new ArrayList<>();
        res.add(new Group("[0,0.5)",0.0));
        res.add(new Group("[0.5,1)", 0.0));
        res.add(new Group("[1.0,1.5)", 0.0));
        res.add(new Group("[1.5,2.0)",0.0));
        res.add(new Group("[2.0,2.5)", 0.0));
        res.add(new Group("[2.5,3.0)",0.0));
        res.add(new Group("[3, )",0.0));

        return res;
    }

    public static List<Group> visitGroup() {
        List<Group> res = new ArrayList<>();
        res.add(new Group("[0,20)",0.0));
        res.add(new Group("[20,50)", 0.0));
        res.add(new Group("[50,100)", 0.0));
        res.add(new Group("[100,200)",0.0));
        res.add(new Group("[200,500)", 0.0));
        res.add(new Group("[500,1000)",0.0));
        res.add(new Group("[1000, )",0.0));
        return res;
    }

}
