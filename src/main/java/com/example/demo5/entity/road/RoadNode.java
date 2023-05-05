package com.example.demo5.entity.road;

import com.example.demo5.common.GeoPoint;
import lombok.Data;

/**
 *
 * @author huyue87
 * @date 2020-11-14 13:53
 * 用于路段建图的节点
 */

@Data
public class RoadNode {

    /**
     * nodeid(指路段数据中的start_id, end_id)
     */
    private Long nodeId;
    /**
     * 路段对应的地理坐标点（116，36）
     */
    private GeoPoint geoPoint;

    /**
     * 构造函数
     * @param nodeId 节点编号
     * @param geoPoint 地理位置
     */
    public RoadNode(Long nodeId, GeoPoint geoPoint) {
        this.nodeId = nodeId;
        this.geoPoint = geoPoint;
    }
}
