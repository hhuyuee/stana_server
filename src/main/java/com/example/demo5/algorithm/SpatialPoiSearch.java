package com.example.demo5.algorithm;

import com.example.demo5.common.RTreeIndexPoiOper;
import com.example.demo5.entity.Poi;
import com.example.demo5.util.GeoUtils;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * poi空间查询
 *
 * @author huyue87@jd.com
 * @date 2020/12/10 16:47
 * @since 2.0.0
 */
public class SpatialPoiSearch {
    /**
     * 索引
     */
    private RTreeIndexPoiOper rTreeIndexPoiOper;

    /**
     * 查询的数据
     */
    List<Poi> dataList;

    /**
     * 构造函数
     */
    public SpatialPoiSearch(List<Poi> dataList) {
        this.dataList = dataList;
        build();
    }
    /**
     * 建立索引
     */
    public void build() {
        rTreeIndexPoiOper = new RTreeIndexPoiOper();
        for (Poi data : dataList) {
            rTreeIndexPoiOper.add(data);
        }
        rTreeIndexPoiOper.buildIndex();
    }

    /**
     * 空间范围○查询
     */
    public List<Poi> getTargetList(Geometry geometry, double bufLen){
        List<Poi> targetList = new ArrayList<>();
        Geometry bufferGeom = geometry.buffer(bufLen);
        // 寻找在缓冲范围内所有符合条件的驻留点
        if(bufferGeom instanceof Polygon) {
            Polygon buffer = (Polygon) bufferGeom;
            targetList = rTreeIndexPoiOper.searchIntersect(buffer, true).stream().map(g ->(Poi)g).collect(Collectors.toList());
        }
        return targetList;
    }

    private Geometry getPoint(Poi poi) {
        String tmp = "POINT(" + poi.getLng() + " " + poi.getLat() + ")";
        return GeoUtils.wkt2Geometry(tmp);
    }

}
