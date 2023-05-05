package com.example.demo5.common;
import com.example.demo5.entity.StayPoint;
import com.example.demo5.util.GeoUtils;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.index.strtree.STRtree;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author huyue87
 * @date 2020-11-09 10:16 2020-11-11 09:58
 * RTree索引 针对地理位置
 */
public class RTreeIndexStOper {
    /**
     * rtree 索引对象
     */
    private STRtree stRtree;

    /**
     * R tree
     * @param geometries 含有地理对象的类 SRTree的数据类型是驻留点
     */
    public RTreeIndexStOper(List<StayPoint> geometries) {
        stRtree = new STRtree();
        geometries.forEach(g -> {
            Geometry gCentroid = GeoUtils.wkt2Geometry(g.getCentroid());
            Envelope bbox = gCentroid.getEnvelopeInternal();
            stRtree.insert(bbox, g);
        });
    }

    /**
     * 初始化R tree
     */
    public RTreeIndexStOper() {
        stRtree = new STRtree();
    }

    /**
     * 增加staypoint作为节点
     * @param geometry
     */
    public void add(StayPoint geometry) {
        if(geometry == null) {
            return;
        }
        Geometry gCentroid = GeoUtils.wkt2Geometry(geometry.getCentroid());
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
    public List<StayPoint> searchIntersect(Geometry g, boolean parallel) {
        if(g == null) {
            return null;
        }
        Envelope bbox = g.getEnvelopeInternal();
        List<StayPoint> candidates = stRtree.query(bbox);
        if(parallel) {
            return candidates.parallelStream().filter(o -> !g.equals(GeoUtils.wkt2Geometry(o.getCentroid())) && g.intersects(GeoUtils.wkt2Geometry(o.getCentroid()))).collect(Collectors.toList());
        } else {
            return candidates.stream().filter(o -> !g.equals(GeoUtils.wkt2Geometry(o.getCentroid())) && g.intersects(GeoUtils.wkt2Geometry(o.getCentroid()))).collect(Collectors.toList());
        }
    }
}
