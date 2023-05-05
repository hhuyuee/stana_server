package com.example.demo5.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author huyue87
 * @date 2020-11-13 17:10 2020-11-13 17:32
 * 路段数据 <- 数据库
 */
@Data
@TableName(value ="road_net")
public class RoadData implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    /**
     * 用于和驻留点的联立查询
     */
    @TableField(exist = false)
    private String tid;


    @TableId(type = IdType.AUTO)
    /**
     * 主键
     */
    private Integer gid;
    /**
     * 数据库中路段id
     */
    private Long roadId;
    /**
     * 起始点id
     */
    private Long startId;
    /**
     * 终点id
     */
    private Long endId;
    /**
     * 路段长度
     */
    private Double roadLen;
    /**
     * 路段方向 -> 暂时用不上
     */
    private Long direction;
    /**
     * 最大长度
     */
    private Long maxLength;
    /**
     * 路段行驶的最快速度
     */
    private Long maxSpeed;
    /**
     * 路段等级
     */
    private Integer level;
    /**
     * 路段名称
     */
    private String name;
    /**
     * 路网geom linestring的格式
     */
    private String geom;

}
