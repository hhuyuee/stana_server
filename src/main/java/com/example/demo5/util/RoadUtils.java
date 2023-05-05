package com.example.demo5.util;

import com.example.demo5.common.GeoPoint;
import com.example.demo5.entity.RoadData;
import com.example.demo5.entity.road.RoadNet;
import com.example.demo5.entity.road.RoadNetGraph;
import com.example.demo5.entity.road.RoadNode;
import com.example.demo5.entity.road.RoadSegment;
import org.jgrapht.Graph;
import org.locationtech.jts.io.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-15 21:26
 * 对路网RoadNet建图
 */
public class RoadUtils {
    /**
     * roadData-> roadNet
     * @param roadData 路网数据
     * @return 路网
     */
    public static RoadNet roadData2RoadNet(List<RoadData> roadData) throws ParseException {
        List<RoadSegment> roadSegments = new ArrayList<>();
        for (RoadData r : roadData) {
            List<GeoPoint> geoPoints = GeoUtils.line2Point(r);
            RoadNode startNode = new RoadNode(r.getStartId(), geoPoints.get(0));
            RoadNode endNode = new RoadNode(r.getEndId(), geoPoints.get(geoPoints.size() - 1));
            geoPoints.remove(0);
            geoPoints.remove(geoPoints.size() - 1);
            RoadSegment roadSegment = new RoadSegment(r.getRoadId(),
                    startNode,
                    endNode,
                    geoPoints,
                    r.getRoadLen(),
                    r.getDirection(),
                    r.getMaxLength(),
                    r.getMaxSpeed(),
                    r.getLevel(),
                    r.getName());
            roadSegments.add(roadSegment);
        }
        // 初始化路网
        return new RoadNet(roadSegments);
    }
    /**
     * 路网建图
     * @param roadNet 路网
     * @return 图
     */
    public static Graph<RoadNode, RoadSegment> roadNet2Graph(RoadNet roadNet) {
        Graph<RoadNode, RoadSegment> graph = RoadNetGraph.createGraph(roadNet);
        return graph;
    }
}
