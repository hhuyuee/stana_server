package com.example.demo5.entity.vo;

import com.example.demo5.entity.ChartData2;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/11/26 19:59
 * @since 2.0.0
 * 用于交互的单个候选地点特征
 */
@Data
@Accessors(chain = true)
public class CandidatePlaceVo {
    /**
     * 中心经度
     */
    double lng;
    /**
     * 中心纬度
     */
    double lat;
    /**
     * 总访问次数
     */
    Integer visitSum;
    /**
     * 访问对象个数
     */
    Integer visitObj;
    /**
     * 平均访问时长
     */
    double durationAvg;
    /**
     * 一级路网长度
     */
    double road1len;
    /**
     * 二级路网长度
     */
    double road2len;
    /**
     * 三级路网长度
     */
    double road3len;
    /**
     * 交叉路口
     */
    int intersection;
    /**
     * 平均访问频次
     */
    private double freqMean;
    /**
     * 最常访问时间段
     */
    private String timeSegment;
    /**
     * 候选地点闭包-> 用于展示
     */
    String concaveHull;

    /**
     * 地点名称
     */
    String name;

    /**
     * 统计表格数据
     */
    ChartData2 chartData;

    public CandidatePlaceVo() {

    }
}
