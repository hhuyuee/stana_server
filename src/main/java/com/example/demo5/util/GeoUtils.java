package com.example.demo5.util;

import com.example.demo5.common.GeoPoint;
import com.example.demo5.entity.RoadData;
import com.example.demo5.entity.StayPoint;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKTReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-09 10:16 2020-11-10 15:39
 * 用于处理地理数据的工具包
 */
public class GeoUtils {
    /**
     *计算两点的距离
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 返回单位为米
     */
    public static double getDistanceOfMeter(double lat1, double lng1,
                                            double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10;
        return s;
    }

    /**
     * 转换经纬度
     * @param d
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
    /**
     * 地球半径：6378.137KM
     */
    private static double EARTH_RADIUS = 6378.137;

    /**
     * wkb格式的转换为wkt
     * @param wkb
     * @return
     */
    private static Geometry string2Geo(String wkb) throws ParseException {
        byte[] bytes = WKBReader.hexToBytes(wkb);
        WKBReader wkbReader = new WKBReader();
        return wkbReader.read(bytes);
    }
    /**
     * 解析lineStirng
     */
    public static List<GeoPoint> line2Point(RoadData roadData) throws ParseException {

        String source = string2Geo(roadData.getGeom()).toString();
        int indexleft = source.indexOf("(");
        int indexright = source.indexOf(")");
        String sub = source.substring(indexleft + 2, indexright);
        List<String> stringList = Arrays.asList(sub.split(", "));
        List<GeoPoint> points = new ArrayList<>();
        for (String string : stringList) {
            String[] stringArray = string.split(" ");
            GeoPoint point = new GeoPoint(Double.parseDouble(stringArray[0]), Double.parseDouble(stringArray[1]));
            points.add(point);
        }
        return points;
    }

    /**
     * 将wkt转换为geometry
     * @param wkt
     * @return
     */
    public static Geometry wkt2Geometry(String wkt) {
        try {
            WKTReader reader = new WKTReader();
            return reader.read(wkt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得一堆驻留点的质心
     * @param stayPointList
     * @return
     */
    public static GeoPoint getCenter(List<StayPoint> stayPointList) {
//        List<GeoPoint> geoPoints = new ArrayList<>();
        double centroidLng = 0.0;
        double centroidLat = 0.0;
        for (StayPoint stayPoint : stayPointList) {
            centroidLat += stayPoint.getLat();
            centroidLng += stayPoint.getLng();
        }
        return new GeoPoint(centroidLng/stayPointList.size(), centroidLat/stayPointList.size());
    }

    /**
     * 获得一堆驻留点的质心
     * @param coordinateList
     * @return
     */
    public static GeoPoint getCenterCoordinates(List<Coordinate> coordinateList) {
//        List<GeoPoint> geoPoints = new ArrayList<>();
        double centroidLng = 0.0;
        double centroidLat = 0.0;
        for (Coordinate point : coordinateList) {
            // 经度 纬度
            centroidLat += point.getY();
            centroidLng += point.getX();
        }
        return new GeoPoint(centroidLng /coordinateList.size(), centroidLat / coordinateList.size());
    }

    /**
     * 将公里数转换为度数
     */
    /**
     * 将公里数转换为度数
     * @param l
     * @return
     */
    public static Double km2Degree(Double l){
        //公式:l(弧长)=degree（圆心角）× π（圆周率）× r（半径）/180
        //转换后的公式：degree（圆心角）=l(弧长) × 180/(π（圆周率）× r（半径）)
        Double degree = (180/ EARTH_RADIUS / Math.PI)*l;
        return degree;
    }
}
