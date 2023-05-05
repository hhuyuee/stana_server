package com.example.demo5;

import com.example.demo5.entity.RoadData;
import com.example.demo5.entity.road.RoadNet;
import com.example.demo5.entity.road.RoadNode;
import com.example.demo5.entity.road.RoadSegment;
import com.example.demo5.service.RoadDataService;
import com.example.demo5.service.StayPointService;
import com.example.demo5.util.RoadUtils;
import org.jgrapht.Graph;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class RoadNetTest {
    @Autowired
    StayPointService stayPointService;
    @Autowired
    RoadDataService roadDataService;
    @Test
    public void test() throws ParseException, IOException {
//        List<StayPoint> stayPoints = stayPointService.searchAll();
        // 计算mbr
//        String mbr = Mbr.getMinimunMbr(stayPoints);

//        List<RoadData> roadData = roadDataService.searchSpatial(mbr);
        List<RoadData> roadData = roadDataService.searchAll();
        RoadNet roadNet = RoadUtils.roadData2RoadNet(roadData);
        Graph<RoadNode, RoadSegment> graph = RoadUtils.roadNet2Graph(roadNet);
//        System.out.println(roadData.size());
//        // 对road_data 进行处理 之后写到程序里
//        List<RoadSegment> roadSegments = new ArrayList<>();
//        for (RoadData r : roadData) {
//            List<GeoPoint> geoPoints = GeoUtils.line2Point(r);
//            RoadNode startNode = new RoadNode(r.getStartId(), geoPoints.get(0));
//            RoadNode endNode = new RoadNode(r.getEndId(), geoPoints.get(geoPoints.size() - 1));
//            geoPoints.remove(0);
//            geoPoints.remove(geoPoints.size() - 1);
//            RoadSegment roadSegment = new RoadSegment(r.getRoadId(),
//                    startNode,
//                    endNode,
//                    geoPoints,
//                    r.getRoadLen(),
//                    r.getDirection(),
//                    r.getMaxLength(),
//                    r.getMaxSpeed(),
//                    r.getLevel(),
//                    r.getName());
//            roadSegments.add(roadSegment);
//        }
//        // 路网 size: 39072
//        System.out.println(roadSegments.size());
//        // 初始化路网
//        RoadNet roadNet =  new RoadNet(roadSegments);
//
//        Graph<RoadNode, RoadSegment> graph = RoadNetGraph.createGraph(roadNet);
//        File file = new File("D:\\task2\\data_11_24\\intersect.txt");
//        Writer out = new FileWriter(file);
        int count = 0;
        for(RoadNode roadNode : graph.vertexSet()) {
            if(graph.degreeOf(roadNode) > 2) {

                String intersect = roadNode.getGeoPoint().toString() + "\t" +  graph.degreeOf(roadNode) + "\n";
                System.out.println(intersect);
//                out.write(intersect);
                System.out.println(roadNode.getNodeId() + "交叉路口");
                count++;
            }
        }
        System.out.println(count);


    }
}
