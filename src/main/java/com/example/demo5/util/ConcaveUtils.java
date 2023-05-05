package com.example.demo5.util;

import com.example.demo5.common.GeoPoint;

import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-16 16:06
 * 凸包的工具类
 */
public class ConcaveUtils {
    /**
     * 寻找集合中纬度最小的点-> 找基点
     */
    public static GeoPoint findMinLatPoint(List<GeoPoint> pointList) {
        GeoPoint minPt = pointList.get(0);
        double minLat = minPt.getLat();
        for (GeoPoint geoPoint: pointList) {
            if(geoPoint.getLat() < minLat) {
                minLat = geoPoint.getLat();
                minPt = geoPoint;
            }
        }
        return minPt;
    }

    /**
     * 计算叉积 小于0 p1 在 p2的逆时针方向，p0p1的极角大于p0p2
     * @param pt0 基点
     * @param pt1 点1
     * @param pt2 点2
     * @return 叉积
     */
    public static double crossProduct(GeoPoint pt0, GeoPoint pt1, GeoPoint pt2) {
        return (pt1.getLng() - pt0.getLng()) * (pt2.getLat() - pt0.getLat())  - (pt2.getLng() - pt0.getLng()) * (pt1.getLat() - pt0.getLat());
    }

    /**
     * 计算两点距离
     * @param pt1 点1
     * @param pt2 点2
     * @return
     */
    public static double calDistance(GeoPoint pt1, GeoPoint pt2) {
        return GeoUtils.getDistanceOfMeter(pt1.getLat(), pt1.getLng(), pt2.getLat(), pt2.getLng());
    }

}

