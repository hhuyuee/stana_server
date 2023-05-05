package com.example.demo5.entity.geom;

import com.example.demo5.common.GeoPoint;
import lombok.Data;

import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-16 15:55
 * 多边形 -> 返回凸包
 */

@Data
public class Polygon {
    /**
     * 多边形-> polygon、一组地理位置点
     */
    List<GeoPoint> points;

    /**
     * 构造函数
     * @param points 地理位置点
     */
    public Polygon(List<GeoPoint> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        String result ="";
        if (points.size() < 3) {
            return null;
        }
        result += "POLYGON((";
        for (int i = 0; i < points.size(); i++) {
            GeoPoint point = points.get(i);
            if(i == points.size() - 1) {
                result += point.getLng() + " " + point.getLat() + "))";
            } else {
                result += point.getLng() + " " + point.getLat()+",";
            }
        }
        return result;
    }


}
