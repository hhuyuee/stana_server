package com.example.demo5.entity.road;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-15 18:00
 * 根据路段、路节点的路网数据
 */
@Data
public class RoadNet {
    /**
     * 路段集合
     */
    List<RoadSegment> roadSegmentList;
    /**
     * 根据eid 索引 roadsegment
     */
    private Map<Long, RoadSegment> roadSegmentIdMap;
    /**
     * 构造函数
     */
    public RoadNet(List<RoadSegment> roadSegments) {
        // 对路段进行索引
        initIndex();
        for (RoadSegment roadSegment: roadSegments) {
            addIndex(roadSegment);
        }

    }

    /**
     * 初始化索引
     */
    private void initIndex() {
        this.roadSegmentList = new ArrayList<>();
        this.roadSegmentIdMap = new HashMap<>();
    }
    /**
     * 增加索引
     */
    public void addIndex(RoadSegment roadSegment) {
        this.roadSegmentList.add(roadSegment);
        // 将segmentid 和 路段对应
        this.roadSegmentIdMap.put(roadSegment.getSegmentID(),roadSegment);
    }
}
