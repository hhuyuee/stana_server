package com.example.demo5.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

import java.util.Map;
/**
 * 统计值 ，用于渲染表格
 *
 * @author huyue87@jd.com
 * @date 2020/12/14 15:34
 * @since 2.0.0
 */
@Data
@Accessors(chain = true)
public class ChartData {

    /**
     * 驻留点时长分布
     */
    List<Group> durationDistribute;
    /**
     * 停留时间分布（周）
     */
    List<Group> weekdayDistribute;

    /**
     * 停留时间分布 （日）
     */
    List<Group> timeDistribute;

    /**
     * 交叉路口数目
     */
    List<Group> intersectDistribute;

    /**
     * poi分布
     */
    List<Map<String, Object>> poiDistribute;

    /**
     * 1级道路分布
     */
    List<Group> road1Distribute;

    /**
     * 2级道路分布
     */
    List<Group> road2Distribute;

    /**
     * 3级道路分布
     */
    List<Group> road3Distribute;

    /**
     * 访问频率分布
     */
    List<Group> freqDistribute;

    /**
     * 候选地点的访问次数分布
     */
    List<Group> visitSumDistribute;
    /**
     * 平均访问频次
     */
    List<Group> freqMeanDistribute;
    /**
     * 平均访问时长分布
     */
    List<Group> durationAvgDistribute;
    /**
     * 访问对象分布
     */
    List<Group> visitObjStatDistribute;
    /**
     * 周围poi分布
     */
    List<Map<String, Object>> poiTypeDistribute;
    /**
     * 访问时间分布
     */
    List<Group> timeSegDistribute;
    /**
     * 一级路网
     */
    List<Group> road1lenDistribute;
    /**
     * 二级路网
     */
    List<Group> road2lenDistribute;
    /**
     * 三级路网
     */
    List<Group> road3lenDistribute;
}
