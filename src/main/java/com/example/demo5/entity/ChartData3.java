package com.example.demo5.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * 所有地点统计值
 *
 * @author huyue87@jd.com
 * @date 2020/12/14 16:04
 * @since 2.0.0
 */
@Data
@Accessors(chain = true)
public class ChartData3 {

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
    /**
     * 交叉路口数目
     */
    List<Group> intersectDistribute;

}
