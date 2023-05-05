package com.example.demo5.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 *
 * @author huyue87
 * @date 2020-11-14 11:49 2020-11-14 13:24
 * 候选地点的生成
 */
@Data
@Accessors(chain = true)
public class CandidatePlace {
    /**
     * 候选地点的mbr
     */
    private String mbr;
    /**
     * 候选地点包含的StayPoint列表
     */

    private List<StayPoint> stayPointList;
    // 地点筛选之后可能还会有 road_net列表、poi列表
    /**
     * poi列表
     */
    private List<Poi> poiList;
    /**
     * 中心经度
     */
    private double lng;
    /**
     * 中心纬度
     */
    private double lat;
    /**
     * 总访问数
     */
    private Integer visitSum;
    /**
     * 不同对象的访问数
     */
    private Integer visitObj;
    /**
     * 平均访问时间：  数据库中存放的是秒
     */
    private double durationAvg;
    /**
     * 最常访问时间段
     */
    private String timeSegment;
    /**
     * 一级路网长度
     */
    private double road1len;
    /**
     * 二级路网长度
     */
    private double road2len;
    /**
     * 三级路网长度
     */
    private double road3len;
    /**
     * 平均访问频次
     */
    private double freqMean;
    /**
     * 交叉路口
     */
    private Integer intersection;

    /**
     * 地点名称
     */
    private String name;

    /**
     * poi 类别
     */
    private List<Map<String, Object>> poiDistribute;
    /**
     * 访问时长分布
     */
    private List<Group> durationDistribute;
    /**
     * 访问年份分布
     */
    private List<Map<String, Object>> yearDistribute;
    /**
     * 访问季节分布
     */
    private List<Map<String, Object>> monthDistribution;
    /**
     * 访问时间段分布
     */
    private List<Group> timeDistribute;
    /**
     * 停留时间分布（周）
     */
    List<Group> weekDistribute;
    /**
     * 访问频率分布
     */
    List<Group> freqDistribute;

    /**
     * 候选地点闭包
     */
    public String polygon;

    public CandidatePlace() {

    }

    /**
     * CandidatePlace构造函数
     * @param stayPoints 候选地点驻留点
     */
    public CandidatePlace(List<StayPoint> stayPoints) {
        this.stayPointList = stayPoints;
    }

    /**
     * 获得候选地点的mbr
     * @return mbr
     */
    public String getMbr() {
        return Mbr.getMinimunMbr(this.stayPointList);
    }
}
