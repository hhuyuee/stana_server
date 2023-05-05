package com.example.demo5.service.imp;

import com.example.demo5.algorithm.DBScanClusterRTree;
import com.example.demo5.common.GeoPoint;
import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.StayPoint;
import com.example.demo5.entity.geom.ConcaveHull;
import com.example.demo5.entity.geom.Polygon;
import com.example.demo5.service.GeoGenerateService1;
import com.example.demo5.util.GeoUtils;
import org.locationtech.jts.io.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/12/30 14:49
 * @since 2.0.0
 */
@Service
public class GeoGenerateService1Impl implements GeoGenerateService1 {

    // 地点生成算法1 针对驻留点数目大于5000的情况
    @Override
    public List<CandidatePlace> geoGenerate1(List<StayPoint> stayPoints, int minPoints, double radius) throws ParseException, IOException {
        int size = stayPoints.size();
        System.out.println("驻留点数目" + size);
        List<StayPoint> stayPointNew = new ArrayList<>();
        // 驻留点数目大于5000进行采样
        for (int i = 0; i < 10000; i++) {
            // 默认当前系统时间的毫秒数作为种子数
            Random random = new Random();
            int index = (int) (random.nextDouble() * size);
            stayPointNew.add(stayPoints.get(index));
        }
//        System.out.println("采样样本数目" + stayPointNew.size());
        // 聚类
        DBScanClusterRTree dbScanClusterRTree = new DBScanClusterRTree(stayPointNew, minPoints, radius);
        dbScanClusterRTree.doCluster();
        // 对驻留点进行分组
        Map<Integer, List<StayPoint>> map = stayPointNew.stream()
                .collect(Collectors.groupingBy(StayPoint::getClusterID));
        List<GeoPoint> geoPoints = new ArrayList<>();
        List<CandidatePlace> candidatePlaces = new ArrayList<>();
        // 取索引
        DBScanClusterRTree dbScanClusterRTree1 = new DBScanClusterRTree(stayPoints);
        // 获取每个地点的中心
        for (Map.Entry<Integer, List<StayPoint>> entry : map.entrySet()) {
            CandidatePlace candidatePlace = new CandidatePlace();
            if(entry.getKey() == 0) {
                continue;
            }
            // 获取地点中心
            GeoPoint geoPoint = GeoUtils.getCenter(entry.getValue());
            geoPoints.add(geoPoint);
            // 求闭包
            List<GeoPoint> geoPointList = new ArrayList<>();
            for (StayPoint stayPoint : entry.getValue()) {
                geoPointList.add(new GeoPoint(stayPoint.getLng(), stayPoint.getLat()));
            }
            List<GeoPoint> polygon = new ConcaveHull(geoPointList).getConcaveHull();
            candidatePlace.setLat(geoPoint.getLat())
                    .setLng(geoPoint.getLng())
                    .setPolygon(new Polygon(polygon).toString());
//            System.out.println("闭包：" + polygon.toString());
            List<StayPoint> stayPointList = dbScanClusterRTree1.getAroundPoints(geoPoint);
            candidatePlace.setStayPointList(stayPointList);
            for (StayPoint stayPoint : stayPointList) {
                stayPoint.setAdjacentPoints(null);
            }
            candidatePlaces.add(candidatePlace);
        }
//
//        for (GeoPoint geoPoint : geoPoints) {
//            CandidatePlace candidatePlace = candidatePlaces.get(seq);
//            List<StayPoint> stayPointList = dbScanClusterRTree1.getAroundPoints(geoPoint);
////            System.out.println("这个簇内驻留点数目" + stayPointList.size());
//            count += stayPointList.size();
//            candidatePlace.setStayPointList(stayPointList);
////            List<GeoPoint> geoPointList = new ArrayList<>();
////            for (StayPoint stayPoint : stayPointList) {
////                geoPointList.add(new GeoPoint(stayPoint.getLng(), stayPoint.getLat()));
////            }
////            List<GeoPoint> polygon = new ConcaveHull(geoPointList).getConcaveHull();
//////             候选地点的闭包
////            candidatePlace.setPolygon(new Polygon(polygon).toString());
////            candidatePlace.setPolygon(new Polygon(concaveHulls.get(seq)).toString());
//            // 置邻接节点为空，防止栈溢出
//            for (StayPoint stayPoint : stayPointList) {
//                stayPoint.setAdjacentPoints(null);
//            }
////            candidatePlaces.set(seq, candidatePlace);
//            seq ++;
//        }
//        List<CandidatePlace> candidatePlaces = new ArrayList<>();
//        System.out.println("参与到驻留点数目" + count);
        return candidatePlaces;

    }
}
