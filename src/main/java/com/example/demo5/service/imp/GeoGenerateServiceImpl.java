package com.example.demo5.service.imp;

import com.example.demo5.algorithm.DBScanClusterRTree;
import com.example.demo5.algorithm.Hierarchical;
import com.example.demo5.common.GeoPoint;
import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.StayPoint;
import com.example.demo5.entity.geom.ConcaveHull;
import com.example.demo5.entity.geom.Polygon;
import com.example.demo5.service.GeoGenerateService;
import com.example.demo5.util.GeoUtils;
import org.locationtech.jts.io.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-15 21:58
 * 地点生成动态服务类
 */
@Service
public class GeoGenerateServiceImpl implements GeoGenerateService {
    /**
     * 引入密度聚类算法
     * @return
     */
    @Override
    public List<CandidatePlace> geoGenerate(List<StayPoint> stayPoints, int minPoints, double radius) throws ParseException, IOException {
        DBScanClusterRTree dbScanClusterRTree = new DBScanClusterRTree(stayPoints, minPoints, radius);
        dbScanClusterRTree.doCluster();
        Map<Integer, List<StayPoint>> map = stayPoints.stream()
                .collect(Collectors.groupingBy(StayPoint::getClusterID));
        List<CandidatePlace> result = new ArrayList<>();
        Integer count = 0;
        // 遍历map
        for(Map.Entry<Integer, List<StayPoint>> entry : map.entrySet()) {
            // 噪声点
            if(entry.getKey() == 0) {
//                System.out.println("噪声点数目" + entry.getValue().size());
//                System.out.println((double) entry.getValue().size() / stayPoints.size());
                continue;
            }
            CandidatePlace candidatePlace = new CandidatePlace(entry.getValue());
            List<GeoPoint> geoPointList = new ArrayList<>();
            for (StayPoint stayPoint : entry.getValue()) {
                geoPointList.add(new GeoPoint(stayPoint.getLng(), stayPoint.getLat()));
            }
            List<GeoPoint> polygon = new ConcaveHull(geoPointList).getConcaveHull();
            // 候选地点的闭包
            candidatePlace.setPolygon(new Polygon(polygon).toString());

            // 候选地点的质心
            GeoPoint geoPoint = GeoUtils.getCenter(candidatePlace.getStayPointList());
            // 候选地点的中心坐标
            candidatePlace.setLng(geoPoint.getLng())
                    .setLat(geoPoint.getLat());
            // 候选地点名称
            candidatePlace.setName("cluster_" + count);
            count ++;

            // 候选集合地点生成
            List<StayPoint> stayPointList = candidatePlace.getStayPointList();
            // 置邻接节点为空，防止栈溢出
            for (StayPoint stayPoint : stayPointList) {
                stayPoint.setAdjacentPoints(null);
            }
            result.add(candidatePlace);
        }
        return result;
    }

    @Override
    public List<CandidatePlace> geoGenerate(List<StayPoint> stayPoints, double thresHold) throws IOException {
        Hierarchical hierarchical = new Hierarchical(thresHold, stayPoints);
        List<CandidatePlace> result = hierarchical.hierarchicalResult();
        int ClusterID = 0;
        for(CandidatePlace candidatePlace : result) {
            List<StayPoint> tmp = candidatePlace.getStayPointList();
            ClusterID++;
        }
        return result;
    }
}
