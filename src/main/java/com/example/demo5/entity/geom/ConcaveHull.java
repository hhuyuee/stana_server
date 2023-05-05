package com.example.demo5.entity.geom;

import com.example.demo5.common.GeoPoint;
import com.example.demo5.util.ConcaveUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-16 15:44
 * 凸包-> 用于展示地点生成
 */
@Data
public class ConcaveHull {
    /**
     * 生成凸包的地理点序列
     */
    private List<GeoPoint> geoPointList;

    /**
     * 基点
     */
    private GeoPoint basePoint;

    /**
     * 构造函数
     * @param geoPoints 地理点序列
     */
    public ConcaveHull(List<GeoPoint> geoPoints) {

        Set<GeoPoint> geoPointSet = new HashSet<>(geoPoints);
        List<GeoPoint> points = new ArrayList<>(geoPointSet);

        this.geoPointList = points;
        // 寻找基点
        this.basePoint = ConcaveUtils.findMinLatPoint(points);

    }

    /**
     * 排列
     * @param pt1 点1
     * @param pt2 点2
     * @return 排列顺序
     */
    public int compare(GeoPoint pt1, GeoPoint pt2) {
        double temp = ConcaveUtils.crossProduct(basePoint, pt1, pt2);
        if(temp == 0) {
            if (ConcaveUtils.calDistance(basePoint, pt1) < ConcaveUtils.calDistance(basePoint, pt2)) {
                return 1;
            } else {
                return -1;
            }
        }
        return (temp > 0) ? 1 : -1;
    }
    /**
     * 生成闭包
     * @return 闭包边界点
     */
    public List<GeoPoint> getConcaveHull() {
       List<GeoPoint> result = new ArrayList<>();
       int top = 2;
       // 去除basePoint
       geoPointList.remove(basePoint);
       // 在result中增加basePoint
       result.add(basePoint);
       // TODO 这里可能存在问题
       if(geoPointList.size() < 2) {
           return geoPointList;
       }
       // 对geoPointList排序
       geoPointList.sort(this::compare);
       result.add(geoPointList.get(0));
       result.add(geoPointList.get(1));
       for(int index = 2; index < geoPointList.size(); index++) {
           while(top > 0 && ConcaveUtils.crossProduct(result.get(top - 1), geoPointList.get(index), result.get(top)) < 0) {
               --top;
               result.remove(result.size() - 1);
           }
           result.add(geoPointList.get(index));
           top++;
       }
       // 增加基点，形成闭包
       result.add(basePoint);
       return result;
    }
}
