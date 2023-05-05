package com.example.demo5.service;

import com.example.demo5.entity.Group;
import com.example.demo5.entity.Poi;
import com.example.demo5.entity.StayPoint;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-19 11:40
 * 驻留点统计服务类
 */

public interface PointStatService {

    /**
     * 获得最早时间
     * @param stayPointList
     * @return
     */
    Date getStartTime(List<StayPoint> stayPointList);

    /**
     * 获得最晚时间
     * @param stayPointList
     * @return
     */
    Date getEndTime(List<StayPoint> stayPointList);

    /**
     * 时长统计
     * @param stayPointList 列表
     * @return 统计结果
     */
    List<Group> durationStat(List<StayPoint> stayPointList);

    /**
     * 星期统计
     * @param stayPointList 列表
     * @return 统计结果
     */
    List<Group> weekDayStat(List<StayPoint> stayPointList);
    /**
     * 时间段统计
     * @param stayPointList 列表
     * @return 统计结果
     */
    List<Group> timeStat(List<StayPoint> stayPointList);
    /**
     * poi统计
     * @param poiList 列表
     * @return 统计结果
     */
    List<Map<String, Object>> poiStat(List<Poi> poiList);
    /**
     * 路网统计
     * @param roadLenSum 列表
     * @return 统计结果
     */
    List<Group> roadStat(List<Double> roadLenSum);

    /**
     * 交叉路口统计
     * @param stayPointList 列表
     * @return 统计结果
     */
    List<Group> intersectStat(List<StayPoint> stayPointList);


}
