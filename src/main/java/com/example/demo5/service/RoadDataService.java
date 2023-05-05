package com.example.demo5.service;

import com.example.demo5.entity.RoadData;

import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-14 17:08
 * 路网数据的映射
 */
public interface RoadDataService{
    /**
     * 路网数据的全量查询
     * @return
     */
    List<RoadData> searchAll();
    /**
     * 路网数据的空间范围查询
     */
    List<RoadData> searchSpatial(String mbrString);
}
