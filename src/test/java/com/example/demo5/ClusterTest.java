package com.example.demo5;

import com.example.demo5.service.*;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ClusterTest {
    @Autowired
    StayPointService stayPointService;
    @Autowired
    GeoGenerateService geoGenerateService;
    @Autowired
    RoadDataService roadDataService;
    @Autowired
    PoiService poiService;
    @Autowired
    PlaceStatService statService;

    @Test
    void test() throws IOException, ParseException {
//         spatial join 实现
//        List<Map<String, Object>> maps = stayPointService.stayPointWithPoi(50, "超市商场");
//        // 转换为驼峰式
//        List<Map<String, Object>> collect = maps.stream()
//                .map(MapUtil::toCamelCaseMap)
//                .collect(Collectors.toList());
//        List<StayPoint> stayPoints = new ArrayList<>();
//        for (Map<String, Object> tmp : collect) {
//            String temp = JSON.toJSONString(tmp);
//            StayPoint single = JSON.parseObject(temp, StayPoint.class);
//            stayPoints.add(single);
//        }
//        List<CandidatePlace> candidatePlaces = geoGenerateService.geoGenerate(stayPoints, 10, 0.005);
//        System.out.println(candidatePlaces.size());
//        // 对候选地点的处理
//        // 文件
//        File file = new File("D:\\polygon6.txt");
//        Writer out = new FileWriter(file);
//        List<Polygon> polygonList = new ArrayList<>();
//        for (CandidatePlace candidatePlace : candidatePlaces) {
//            System.out.println("---------------------------------");
//            statService.featureStillExtract(candidatePlace, 0.03);
//            System.out.println("交叉路口数: " + candidatePlace.getIntersection());
//            for (Map.Entry<Integer, Long> entry :candidatePlace.getRoadLevel().entrySet()) {
//                System.out.println("路段" + entry.getKey() + "数目:" + entry.getValue());
//            }
//            for(Map.Entry<String, Long> entry : candidatePlace.getPoiType().entrySet()) {
//                if(entry.getKey().equals("unknown")) {
//                    continue;
//                }
//                System.out.println(entry.getKey() + "数目:" + entry.getValue());
//            }
//            statService.featureDynamicExtract(candidatePlace);
//            System.out.println("总访问数:" + candidatePlace.getVisitSum());
//            System.out.println("不同对象访问数: " + candidatePlace.getVisitSet());
//            System.out.println("平均访问时长:" + candidatePlace.getMeanTime() + "min");
//            for (Map.Entry<String, Long> entry : candidatePlace.getTimeDistribute().entrySet()) {
//                System.out.println(entry.getKey() + "驻留点数目:" + entry.getValue());
//            }
//            // 加入闭包测试
//            System.out.println(candidatePlace.getPolygon());
//
//        }

    }
}
