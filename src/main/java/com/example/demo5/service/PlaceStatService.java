package com.example.demo5.service;

import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.PlaceInfo;
import org.locationtech.jts.io.ParseException;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-16 10:04
 * 提供平台各类统计功能-> 包括筛选出驻留点的统计特征、地块的统计特征
 */
public interface PlaceStatService {

    /**
     * 提取候选地点特征
     * @param candidatePlace 候选地点
     */
    void featureStillExtract(CandidatePlace candidatePlace, double radius) throws ParseException;

    /**
     * 提取驻留点集合特征
     * @param candidatePlace 驻留点集合
     */
    void featureDynamicExtract(CandidatePlace candidatePlace);

    /**
     * 获取全量的地点统计信息
     * @param candidatePlaces 后续地点列表
     * @return
     */
    List<CandidatePlace> getSingleFeature(List<CandidatePlace> candidatePlaces) throws ParseException, ExecutionException, InterruptedException;

    PlaceInfo getPlaceInfo(List<CandidatePlace> candidatePlaces) throws ParseException, ExecutionException, InterruptedException;
}
