package com.example.demo5.common;

import com.example.demo5.entity.Poi;
import com.example.demo5.util.GeoUtils;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.index.strtree.STRtree;

import java.util.List;
import java.util.stream.Collectors;

/**
 * poi 空间索引
 *
 * @author huyue87@jd.com
 * @date 2020/12/11 10:08
 * @since 2.0.0
 */
public class RTreeIndexPoiOper {
    /**
     * rtree 索引对象
     */
    private STRtree stRtree;

    /**
     * R tree
     * @param geometries 含有地理对象的类 SRTree的数据类型是驻留点
     */
    public RTreeIndexPoiOper(List<Poi> geometries) {
        stRtree = new STRtree();
        geometries.forEach(g -> {
            Geometry gCentroid = getPoint(g);
            Envelope bbox = gCentroid.getEnvelopeInternal();
            stRtree.insert(bbox, g);
        });
    }

    /**
     * 初始化R tree
     */
    public RTreeIndexPoiOper() {
        stRtree = new STRtree();
    }

    /**
     * 增加staypoint作为节点
     * @param geometry
     */
    public void add(Poi geometry) {
        if(geometry == null) {
            return;
        }
        Geometry gCentroid = getPoint(geometry);
        Envelope bbox = gCentroid.getEnvelopeInternal();
        stRtree.insert(bbox, geometry);
    }

    /**
     * 建立索引
     */
    public void buildIndex() {
        stRtree.build();
    }

    /**\
     * 查询相交对象
     * @param g 几何对象
     * @param parallel 并发执行
     * @return
     */
    public List<Poi> searchIntersect(Geometry g, boolean parallel) {
        if(g == null) {
            return null;
        }
        Envelope bbox = g.getEnvelopeInternal();
        List<Poi> candidates = stRtree.query(bbox);
        if(parallel) {
            return candidates.parallelStream().filter(o -> !g.equals(getPoint(o)) && g.intersects(getPoint(o))).collect(Collectors.toList());
        } else {
            return candidates.stream().filter(o -> !g.equals(getPoint(o)) && g.intersects(getPoint(o))).collect(Collectors.toList());
        }
    }

    private Geometry getPoint(Poi poi) {
        String tmp = "POINT(" + poi.getLng() + " " + poi.getLat() + ")";
        return GeoUtils.wkt2Geometry(tmp);
    }
}
