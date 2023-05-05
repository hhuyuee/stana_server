package com.example.demo5.service.imp;

import com.example.demo5.common.GeoPoint;
import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.Group;
import com.example.demo5.entity.Poi;
import com.example.demo5.mapper.PoiMapper;
import com.example.demo5.service.PlaceAllService;
import com.example.demo5.util.StatisticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 地点集合信息的统计
 * 访问次数分布
 * 平均访问频次分布
 * 平均访问时长分布
 * 访问对象个数分布
 * 交叉路口个数
 * 周围poi分布
 * 一、二、三级路网长度分布
 * 访问时间分布
 * 平均访问时长分布
 * @author huyue87@jd.com
 * @date 2020/12/7 18:33
 * @since 2.0.0
 */
@Service
public class PlaceAllServiceImpl implements PlaceAllService {
    @Autowired
    PoiMapper poiMapper;

    @Override
    // 访问次数分布
    public List<Group> visitSumStat(List<CandidatePlace> candidatePlaces) {
        List<Integer> visitSumList = candidatePlaces.parallelStream()
                .map(CandidatePlace::getVisitSum)
                .collect(Collectors.toList());
        return StatisticUtils.visitStat(visitSumList);
    }

    @Override
    // 平均访问频次分布
    public List<Group> freqMeanStat(List<CandidatePlace> candidatePlaces) {
        List<Double> freqMeanList = candidatePlaces.parallelStream()
                .map(CandidatePlace::getFreqMean)
                .collect(Collectors.toList());
        return StatisticUtils.freqStat(freqMeanList);
    }

    @Override
    public List<Group> durationAvgStat(List<CandidatePlace> candidatePlaces) {
        List<Double> durationAvgList = candidatePlaces.parallelStream()
                .map(CandidatePlace::getDurationAvg)
                .collect(Collectors.toList());
        return StatisticUtils.durationStat(durationAvgList);
    }

    @Override
    public List<Group> visitObjStat(List<CandidatePlace> candidatePlaces) {
        List<Integer> visitObjList = candidatePlaces.parallelStream()
                .map(CandidatePlace::getVisitObj)
                .collect(Collectors.toList());
        return StatisticUtils.visitStat(visitObjList);
    }

    @Override
    public List<Group> intersectStat(List<CandidatePlace> candidatePlaces) {
        List<Integer> intersectList = candidatePlaces.parallelStream()
                .map(CandidatePlace::getIntersection)
                .collect(Collectors.toList());
        return StatisticUtils.intersectStat(intersectList);
    }

    private boolean isBeijingPoint(GeoPoint point) {
        if (point.getLat() > 39 && point.getLat() < 42) {
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> poiTypeStat(List<CandidatePlace> candidatePlaces) {
        List<Poi> poiSum = new ArrayList<>();
        for (CandidatePlace candidatePlace : candidatePlaces) {
            GeoPoint geoPoint = new GeoPoint(candidatePlace.getLng(), candidatePlace.getLat());
            String roadTable = "road_net_beijing";
            String poiTable = "poi_beijing";
            if (!isBeijingPoint(geoPoint)) {
                roadTable = "road_net_nantong";
                poiTable = "poi_nantong";
            }
            poiSum.addAll(poiMapper.selectSpatialTest(geoPoint.toString(), poiTable));
        }
        return poiSum.parallelStream()
                .collect(Collectors.groupingBy(Poi::getPoiType, TreeMap::new, Collectors.toList()))
                .entrySet()
                .parallelStream()
                .map(
                        e->{
                            Map<String, Object> item = new TreeMap<>();
                            double prop = (double) e.getValue().size() / poiSum.size();
                            item.put("name", e.getKey());
                            item.put("value", prop);
                            return item;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public List<Group> timeSegStat(List<CandidatePlace> candidatePlaces) {
        List<String> timeSegment = candidatePlaces
                .parallelStream()
                .map(CandidatePlace::getTimeSegment)
                .collect(Collectors.toList());

        return StatisticUtils.timeSegStatPlace(timeSegment);

    }

    @Override
    public List<Group> road1lenStat(List<CandidatePlace> candidatePlaces) {
        List<Double> road1LenList = candidatePlaces
                                    .stream()
                                    .map(CandidatePlace::getRoad1len)
                                    .collect(Collectors.toList());
        return StatisticUtils.roadStat(road1LenList);

    }

    @Override
    public List<Group> road2lenStat(List<CandidatePlace> candidatePlaces) {
        List<Double> road2LenList = candidatePlaces
                .stream()
                .map(CandidatePlace::getRoad2len)
                .collect(Collectors.toList());
        return StatisticUtils.roadStat(road2LenList);
    }

    @Override
    public List<Group> road3lenStat(List<CandidatePlace> candidatePlaces) {
        List<Double> road3LenList = candidatePlaces
                .stream()
                .map(CandidatePlace::getRoad3len)
                .collect(Collectors.toList());
        return StatisticUtils.roadStat(road3LenList);
    }

    @Override
    public List<Map<String, Object>> poiTopNameStat(List<CandidatePlace> candidatePlaces) {
        List<Poi> poiListAll = new ArrayList<>();
        for (CandidatePlace candidatePlace : candidatePlaces) {
            poiListAll.addAll(candidatePlace.getPoiList());
        }
        return null;
    }
}
