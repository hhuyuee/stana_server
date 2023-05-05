package com.example.demo5.service;

import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.StayPoint;
import org.locationtech.jts.io.ParseException;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-15 21:56
 * 地点生成服务类
 */
public interface GeoGenerateService {
    /**
     * DBSCAN RTREE
     * @param stayPoints 驻留点
     * @param minPoints 最小点数
     * @param radius 范围
     * @return 候选地点列表
     * @throws ParseException
     * @throws IOException
     */
    List<CandidatePlace> geoGenerate(List<StayPoint> stayPoints, int minPoints, double radius) throws ParseException, IOException;



    /**
     * 层次聚类
     * @param stayPoints 驻留点
     * @param thresHold 阈值
     * @return 候选地点列表
     * @throws IOException
     */
    List<CandidatePlace> geoGenerate(List<StayPoint> stayPoints, double thresHold) throws IOException;
}
