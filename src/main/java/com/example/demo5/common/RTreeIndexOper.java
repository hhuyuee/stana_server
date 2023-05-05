package com.example.demo5.common;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.index.strtree.STRtree;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于poi数据建立索引
 *
 * @author huyue87@jd.com
 * @date 2020/12/10 16:43
 * @since 2.0.0
 */
public class RTreeIndexOper {
    /**
     * rtree索引对象
     */
    private STRtree strtree;

    /**
     * R树索引辅助类
     *
     * @param geometries 几何对象集合
     */
    public RTreeIndexOper(List<Geometry> geometries) {
        strtree = new STRtree();
        geometries.forEach(g -> {
            Envelope bbox = g.getEnvelopeInternal();
            strtree.insert(bbox, g);
        });
    }

    /**
     * R树索引辅助类
     */
    public RTreeIndexOper() {
        strtree = new STRtree();
    }

    /**
     * 添加geometry
     *
     * @param geom 几何对象
     */
    public void add(Geometry geom) {
        if (geom == null) {
            return;
        }
        Envelope bbox = geom.getEnvelopeInternal();
        strtree.insert(bbox, geom);
    }

    /**
     * 建立索引
     */
    public void buildIndex() {
        strtree.build();
    }

    /**
     * 查询相交对象
     *
     * @param g 查询对象
     * @return 相交的结合对象集合
     */
    public List<Geometry> searchIntersect(Geometry g, boolean parallel) {
        if (g == null) {
            return null;
        }
        Envelope bbox = g.getEnvelopeInternal();
        List<Geometry> candidates = strtree.query(bbox);
        if (parallel) {
            return candidates.parallelStream().filter(o -> !g.equals(o) && g.intersects(o)).collect(Collectors.toList());
        } else {
            return candidates.stream().filter(o -> !g.equals(o) && g.intersects(o)).collect(Collectors.toList());
        }
    }
}
