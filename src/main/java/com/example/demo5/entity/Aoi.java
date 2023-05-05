package com.example.demo5.entity;

import lombok.Data;

import java.util.List;

/**
 * Aoi 实体类
 *
 * @author huyue87@jd.com
 * @date 2021/3/30 16:49
 * @since 2.0.0
 */
@Data
public class Aoi {
    // 地点类型
    String fclass;

    // 地点名称
    String name;

    // 地点坐标
    String geom;

    // 属于该地点的驻留点集合
    List<StayPoint> stayPointList;
}
