package com.example.demo5.algorithm;

import com.example.demo5.common.GeoPoint;
import com.example.demo5.common.RTreeIndexStOper;
import com.example.demo5.entity.StayPoint;
import com.example.demo5.util.GeoUtils;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author huyue87
 * @date 2020-11-09 10:16 2020-11-11 09:50
 * Rtree 完成聚类索引
 */
public class DBScanClusterRTree extends DBScanCluster {
    /**
     * R tree 索引工具
     */
    private RTreeIndexStOper rTreeIndexStOper;
    /**
     * 构造函数
     *
     * @param srcPoints 原始点数据
     * @param minPoints 核要素最小样本点个数
     * @param radius    聚类搜索半径
     */
    public DBScanClusterRTree(List<StayPoint> srcPoints, int minPoints, double radius) throws ParseException {
        super(srcPoints, minPoints, radius);
        buildIndex();
    }
    /**
     * 对现有staypoint建立rtree索引
     */
    public DBScanClusterRTree(List<StayPoint> stayPoints) throws ParseException {
        super(stayPoints);
        buildIndex();
    }

    /**
     * 建立索引 索引是建立在centroid属性上的
     */
    private void buildIndex() throws ParseException {
        if(srcPoints == null) {
            return;
        }
        rTreeIndexStOper = new RTreeIndexStOper();
        for(StayPoint stayPoint : srcPoints) {
            rTreeIndexStOper.add(stayPoint);
        }
        rTreeIndexStOper.buildIndex();
    }

    /**
     * 用RTree寻找目标点相邻的密度直达点
     *
     * @param centerPt 目标点
     * @return 密度直达点
     */
    @Override
    public List<StayPoint> getAdjacentPoints(StayPoint centerPt) throws ParseException {
        if (rTreeIndexStOper == null) {
            super.getAdjacentPoints(centerPt);
        }
        double bufLen;
        bufLen = radius;
        List<StayPoint> adjacentPoints = null;
        // 点的缓冲距离是 圆 -> 是一个polygon
        // 缓冲范围
        Geometry centerPtCentroid = GeoUtils.wkt2Geometry(centerPt.getCentroid());
        Geometry bufferGeom = centerPtCentroid.buffer(bufLen);
        // 寻找在缓冲范围内所有符合条件的驻留点
        if(bufferGeom instanceof Polygon) {
            Polygon buffer = (Polygon) bufferGeom;
            adjacentPoints = rTreeIndexStOper.searchIntersect(buffer, true).stream().map(g -> (StayPoint)g).collect(Collectors.toList());
        }
        return adjacentPoints;
    }

    public List<StayPoint> getAroundPoints(GeoPoint geoPoint) {
        double bufLen;
        // 100m附近
        bufLen = 0.0012;
        List<StayPoint> adjacentPoints = null;
        // 点的缓冲距离是 圆 -> 是一个polygon
        // 缓冲范围
        Geometry centerPtCentroid = GeoUtils.wkt2Geometry(geoPoint.toString());
        Geometry bufferGeom = centerPtCentroid.buffer(bufLen);
        // 寻找在缓冲范围内所有符合条件的驻留点
        if(bufferGeom instanceof Polygon) {
            Polygon buffer = (Polygon) bufferGeom;
            adjacentPoints = rTreeIndexStOper.searchIntersect(buffer, true).stream().map(g -> (StayPoint)g).collect(Collectors.toList());
        }
        return adjacentPoints;
    }
}
