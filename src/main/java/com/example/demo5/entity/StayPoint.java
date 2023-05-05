package com.example.demo5.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-14 11:23 2020-11-14 11:23
 * 驻留点数据(与驻留点数据对应)
 */
@Data
@Accessors(chain = true)
@ToString
public class StayPoint implements Serializable {
    /**
     * 序列id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 驻留点唯一 tid
     */
    private String tid;
    /**
     *  车辆产生的驻留点
     */
    private String oid;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 中间时间
     */
    private Date midTime;
    /**
     * 时间间隔
     */
    private double timeInterval;
    /**
     * 距离间隔
     */
    private double disInterval;
    /**
     * 驻留点中心 4326
     */
    private String centroid;
    /**
     * 中心经度
     */
    private double lng;

    /**
     * 中心纬度
     */
    private double lat;
    /**
     * 驻留mbr
     */
    private String mbr;
    /**
     * 驻留时长
     */
    private double duration;
    /**
     * 行政区划信息
     */
    private String region;
    /**
     * 中心 3857
     */
    private String point;
    /**
     * 聚类编号
     */
    private Integer intersectNum;
    /**
     * 星期
     */
    private String week;
    /**
     * 聚类编号
     */
    private int ClusterID;

    /**
     * 是否被范围
     */
    @JsonIgnore
    private boolean isVisited;
    /**
     * 是否为核心点
     */
    @JsonIgnore
    private boolean isCore;


    /**
     * 邻接点
     */
    @JsonIgnore
    private List<StayPoint> adjacentPoints;

}
