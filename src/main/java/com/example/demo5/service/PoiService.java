package com.example.demo5.service;

import com.example.demo5.entity.Poi;

import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:38
 * poi 服务接口
 */
public interface PoiService {
    /**
     * 按照类别查询poi
     * @param type
     * @return
     */
    List<Poi> searchType(String type);

    /**
     * 空间范围查询poi
     * @param mbr
     * @return
     */
    List<Poi> searchPoiSpatial(String mbr);
}
