package com.example.demo5.service;

import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.StayPoint;
import org.locationtech.jts.io.ParseException;

import java.io.IOException;
import java.util.List;

/**
 * 地点生成采样 方法1
 *
 * @author huyue87@jd.com
 * @date 2020/12/30 14:48
 * @since 2.0.0
 */
public interface GeoGenerateService1 {
    List<CandidatePlace> geoGenerate1(List<StayPoint> stayPoints, int minPoints, double radius) throws ParseException, IOException;
}
