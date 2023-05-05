package com.example.demo5.entity;

import lombok.Data;

/**
 *
 * @author huyue87
 * @date 2020-11-13 17:32 2020-11-14 10:57
 * poi实体 <- 数据库
 */
@Data
public class Poi {
    /**
     * 主键
     */
    private Integer gid;
    /**
     * poi_id
     */
    private String poiId;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 经度
     */
    private double lng;
    /**
     * poi类型
     */
    private Long poiTypeId;
    /**
     * poi名称
     */
    private String poiName;
    /**
     * poi类型
     */
    private String poiType;
    /**
     * poi坐标
     */
    private String geom;

    /**
     * poi 访问次数
     */
    private Integer count;
}
