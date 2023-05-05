package com.example.demo5.entity;

import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-14 14:00
 * mbr 包含方格
 */
public class Mbr {
    /**
     * 经度最小
     */
    private Double lngMin;
    /**
     * 经度最大
     */
    private Double lngMax;
    /**
     * 维度最小
     */
    private Double latMin;
    /**
     * 维度最大
     */
    private Double latMax;

    /**
     * 构造函数
     * @param lngMin
     * @param lngMax
     * @param latMin
     * @param latMax
     */
    public Mbr(Double lngMin, Double lngMax, Double latMin, Double latMax) {
        this.lngMin = lngMin;
        this.lngMax = lngMax;
        this.latMin = latMin;
        this.latMax = latMax;
    }


    @Override
    //POLYGON((116.34368 39.96596,116.34368 39.96672,116.34419 39.96672,116.34419 39.96596,116.34368 39.96596))
    public String toString() {
        return "'POLYGON(("
                + lngMin + " " + latMin + ","
                + lngMax + " " + latMin + ","
                + lngMin + " " + latMax + ","
                + lngMax + " " + latMax + ","
                + lngMin + " " + latMin + "))'";
    }

    /**
     * 返回驻留点集合的最小边框
     * @param stayPointList
     * @return
     */
    public static String getMinimunMbr(List<StayPoint> stayPointList) {
        // 初始化边框  最小的取最大，最大的取最小
        double minLat = Double.MAX_VALUE;
        double minLng = Double.MAX_VALUE;
        double maxLat = Double.MIN_VALUE;
        double maxLng = Double.MIN_VALUE;
        if(stayPointList.isEmpty()) {
            // 列表元素为空
            System.out.println("元素不为空！");
            return null;
        }
        for(StayPoint stayPoint : stayPointList) {
            minLat = Math.min(minLat, stayPoint.getLat());
            minLng = Math.min(minLng, stayPoint.getLng());
            maxLat = Math.max(maxLat, stayPoint.getLat());
            maxLng = Math.max(maxLng, stayPoint.getLng());
        }

        return new Mbr(minLng, maxLng, minLat,maxLat).toString();
    }

}
