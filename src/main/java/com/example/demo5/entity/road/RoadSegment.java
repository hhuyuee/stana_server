package com.example.demo5.entity.road;


import com.example.demo5.common.GeoPoint;
import lombok.Data;

import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-14 16:55
 * 道路的路段信息
 */
@Data
public class RoadSegment {
    /**
     * 道路id 对应 RoadNetGraphroad data id
     */
    private Long segmentID;
    /**
     * 入节点
     */
    private RoadNode startPosition;
    /**
     * 出节点
     */
    private RoadNode endPosition;
    /**
     * 中间点
     */
    private List<GeoPoint> coords;
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
     * 构造函数
     * @param segmentID
     * @param startPosition
     * @param endPosition
     * @param coords
     * @param roadLen
     * @param direction
     * @param maxLength
     * @param maxSpeed
     * @param level
     * @param name
     */
    public RoadSegment(Long segmentID, RoadNode startPosition, RoadNode endPosition, List<GeoPoint> coords, Double roadLen, Long direction, Long maxLength, Long maxSpeed, Integer level, String name) {
        this.segmentID = segmentID;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.coords = coords;
        this.roadLen = roadLen;
        this.direction = direction;
        this.maxLength = maxLength;
        this.maxSpeed = maxSpeed;
        this.level = level;
        this.name = name;
    }


}
