package com.example.demo5.entity.road;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58
 * 用与路网建图
 */
public class RoadNetGraph {
    /**
     * 根据已有的路网进行建立路网图  点： RoadNode
     */
    public static Graph<RoadNode, RoadSegment> createGraph(RoadNet roadNet) {
        // node roadnode， edge graph
        Graph<RoadNode, RoadSegment> graph = new SimpleDirectedWeightedGraph<>(RoadSegment.class);
        System.out.println(roadNet.getRoadSegmentList().size());
        for(RoadSegment roadSegment : roadNet.getRoadSegmentList()) {
            graph.addVertex(roadSegment.getStartPosition());
            graph.addVertex(roadSegment.getEndPosition());
            // 开始结点、结束结点、边
            graph.addEdge(roadSegment.getStartPosition(), roadSegment.getEndPosition(), roadSegment);
            graph.setEdgeWeight(roadSegment, roadSegment.getRoadLen());
//            System.out.println("建图成功");
        }
        return graph;
    }
}
