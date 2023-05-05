package com.example.demo5.service.imp;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo5.entity.*;
import com.example.demo5.entity.vo.PoiInfo;
import com.example.demo5.entity.vo.PoiVo;
import com.example.demo5.entity.vo.StayPointVo;
import com.example.demo5.mapper.IntersectMapper;
import com.example.demo5.mapper.PoiMapper;
import com.example.demo5.mapper.RoadDataMapper;
import com.example.demo5.mapper.StayPointMapper;
import com.example.demo5.service.PointStatService;
import com.example.demo5.service.StayPointService;
import com.example.demo5.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author huyue87
 * @date 2020-11-14 11:23 2020-11-14 11:35
 * 提供有关驻留点查询筛选的服务
 */
@Service
public class StayPointServiceImpl extends ServiceImpl<StayPointMapper, StayPoint> implements StayPointService {
    /**
     * 注入stayPoint映射
     */
    @Autowired
    private StayPointMapper stayPointMapper;
    @Autowired
    private PointStatService pointStatService;
    @Autowired
    private PoiMapper poiMapper;
    @Autowired
    private RoadDataMapper roadDataMapper;
    @Autowired
    private IntersectMapper intersectMapper;


    @Override
    public StayPointInfo searchAll(String tableName) {
        List<StayPoint> stayPointList = stayPointMapper.selectAll(tableName);
        // poi的统计
        List<Poi> poiList = poiMapper.selectAll(tableName);
        // 路网的统计
        List<Double> road1Len = roadDataMapper.selectAll(tableName, 1);
        List<Double> road2Len = roadDataMapper.selectAll(tableName, 2);
        List<Double> road3Len = roadDataMapper.selectAll(tableName, 3);

        // 交叉路口的统计
//        List<Integer> intersectCount = intersectMapper.selectAll(tableName);


        StayPointInfo stayPointInfo = new StayPointInfo();
        stayPointInfo.setStayPointVos(
                stayPointList.stream()
                .map(this::transfer)
                .collect(Collectors.toList())
        );
        stayPointInfo.setVisitNum(stayPointList.size())
                .setStartTime(DateUtils.date2String(pointStatService.getStartTime(stayPointList)))
                .setEndTime(DateUtils.date2String(pointStatService.getEndTime(stayPointList)));
//                .setDurationDistribute(pointStatService.durationStat(stayPointList))
//                .setWeekdayDistribute(pointStatService.weekDayStat(stayPointList))
//                .setTimeDistribute(pointStatService.timeStat(stayPointList))
//                .setPoiDistribute(pointStatService.poiStat(poiList))
//                .setRoad1Distribute(pointStatService.roadStat(road1Len))
//                .setRoad2Distribute(pointStatService.roadStat(road2Len))
//                .setRoad3Distribute(pointStatService.roadStat(road3Len))
//                .setIntersectDistribute(pointStatService.intersectStat(stayPointList));

        return stayPointInfo;
    }





    @Override
    public StayPointInfo searchFilter(FilterConditions filterConditions) {
        List<StayPoint> stayPointList = stayPointMapper.selectAttribute(filterConditions);

//        if (filterConditions.getTimeSequencesStr() != null) {
//        }
//        // poi 统计
//        List<Poi> poiList = stayPointMapper.selectPoi(stayPointList, filterConditions);
//        // 路网统计
//        List<Double> road1Len = stayPointMapper.selectRoad(stayPointList, filterConditions,1);
//        List<Double> road2Len = stayPointMapper.selectRoad(stayPointList, filterConditions,2);
//        List<Double> road3Len = stayPointMapper.selectRoad(stayPointList, filterConditions,3);

        StayPointInfo stayPointInfo = new StayPointInfo();
        stayPointInfo.setStayPointVos(
                stayPointList.stream()
                        .map(this::transfer)
                        .collect(Collectors.toList())
        );
        if(stayPointList.size() == 0) {
            return stayPointInfo;
        }
        stayPointInfo.setVisitNum(stayPointList.size())
                .setStartTime(DateUtils.date2String(pointStatService.getStartTime(stayPointList)))
                .setEndTime(DateUtils.date2String(pointStatService.getEndTime(stayPointList)));
//                .setDurationDistribute(pointStatService.durationStat(stayPointList))
//                .setWeekdayDistribute(pointStatService.weekDayStat(stayPointList))
//                .setTimeDistribute(pointStatService.timeStat(stayPointList))
//                .setPoiDistribute(pointStatService.poiStat(poiList))
//                .setIntersectDistribute(pointStatService.intersectStat(stayPointList));
//                .setRoad1Distribute(pointStatService.roadStat(road1Len))
//                .setRoad2Distribute(pointStatService.roadStat(road2Len))
//                .setRoad3Distribute(pointStatService.roadStat(road3Len));
        return stayPointInfo;
    }

    @Override
    public StayPointInfo searchFilterV2(FilterConditionV2 filterConditionV2) {

        long startTime2 = System.currentTimeMillis();
        List<StayPoint> stayPointList = stayPointMapper.selectAttributeV2(filterConditionV2);
        int size = stayPointList.size();
        System.out.println("总数目" + size);
        long endTime2 = System.currentTimeMillis();
        System.out.println("select total time:" + (endTime2 - startTime2) +"ms");

        long startTime3 = System.currentTimeMillis();
        // poi 统计
        List<Poi> poiList = stayPointMapper.selectPoi(filterConditionV2);
        // poi数目统计
        List<Poi> poiList1 = stayPointMapper.poiNameQuery(filterConditionV2);
        // Poi转PoiVo
        List<PoiVo> poiVos = poiTransfer(poiList1);
        List<Group> roadlenList = stayPointMapper.selectRoadV2(filterConditionV2);
        Map<String, List<Group>> classify = roadlenList.parallelStream()
                .collect(Collectors.groupingBy(Group::getName, TreeMap::new, Collectors.toList()));
        // 增加判断 周围不存在路网等级为1
        if (classify.get("1") == null) {
            List<Group> groups = new ArrayList<>();
            groups.add(new Group("1",0.0));
            classify.put("1", groups);
        }
        if (classify.get("2") == null) {
            List<Group> groups = new ArrayList<>();
            groups.add(new Group("2",0.0));
            classify.put("2", groups);
        }
        if (classify.get("3") == null) {
            List<Group> groups = new ArrayList<>();
            groups.add(new Group("3",0.0));
            classify.put("3", groups);
        }
//        System.out.println("长度为1大小：" + classify.get("1").size());
        StayPointInfo stayPointInfo = new StayPointInfo();
        stayPointInfo.setStayPointVos(
                stayPointList.stream()
                        .map(this::transfer)
                        .collect(Collectors.toList())
        );
        if(stayPointList.size() == 0) {
            return stayPointInfo;
        }
        // 表格数据
        ChartData1 chartData = new ChartData1();
        chartData.setDurationDistribute(pointStatService.durationStat(stayPointList))
                .setWeekdayDistribute(pointStatService.weekDayStat(stayPointList))
                .setTimeDistribute(pointStatService.timeStat(stayPointList))
                .setPoiDistribute(pointStatService.poiStat(poiList))
//                .setIntersectDistribute(pointStatService.intersectStat(stayPointList))
                .setRoad2Distribute(pointStatService.roadStat(object2Double(classify.get("2"), size)))
                .setRoad1Distribute(pointStatService.roadStat(object2Double(classify.get("1"), size)))
                .setRoad3Distribute(pointStatService.roadStat(object2Double(classify.get("3"), size)));

        stayPointInfo.setVisitNum(stayPointList.size())
                .setStartTime(DateUtils.date2String(pointStatService.getStartTime(stayPointList)))
                .setEndTime(DateUtils.date2String(pointStatService.getEndTime(stayPointList)))
                .setPoiNameTop(poiVos)
                .setChartData(chartData);
        long endTime3 = System.currentTimeMillis();
        System.out.println("statistic total time:" + (endTime3 - startTime3) +"ms");
        return stayPointInfo;
    }

    // TODO 用于测试
    @Override
    public List<StayPointVo> searchFilterPoint(FilterConditions filterConditions) {
        List<StayPoint> stayPointList = stayPointMapper.selectAttribute(filterConditions);
        List<StayPointVo> stayPointVos =  stayPointList.stream()
                .map(this::transfer)
                .collect(Collectors.toList());
        return stayPointVos;
    }

    @Override
    public List<StayPoint> stayPointWithPoi(int dis, String type) {
        List<Map<String, Object>> maps = stayPointMapper.stayPointWithPoi(dis, type);
        List<Map<String, Object>> collect = maps.stream()
                .map(MapUtil::toCamelCaseMap)
                .collect(Collectors.toList());
        // 将其解析为staypoint对象
        List<StayPoint> stayPointList = new ArrayList<>();
        for (Map<String, Object> tmp : collect) {
            String temp = JSON.toJSONString(tmp);
            StayPoint single = JSON.parseObject(temp, StayPoint.class);
            stayPointList.add(single);
        }
        return stayPointList;
    }

    @Override
    public List<StayPoint> stayPointWithRoad(int dis, int level) {
        List<Map<String, Object>> maps = stayPointMapper.stayPointWithRoad(dis, level);
        List<Map<String, Object>> collect = maps.stream()
                .map(MapUtil::toCamelCaseMap)
                .collect(Collectors.toList());
        // 将其解析为staypoint对象
        List<StayPoint> stayPointList = new ArrayList<>();
        for (Map<String, Object> tmp : collect) {
            String temp = JSON.toJSONString(tmp);
            StayPoint single = JSON.parseObject(temp, StayPoint.class);
            stayPointList.add(single);
        }
        return stayPointList;
    }
    /**
     * 集合数据类型的转变
     *
     * @param stayPoint 原始主流点数据
     * @return StayPointVo
     * @throws
     * @since 2.0.0
     */
    private StayPointVo transfer(StayPoint stayPoint) {
        StayPointVo stayPointVo = new StayPointVo();
        stayPointVo.setTid(stayPoint.getTid())
                .setOid(stayPoint.getOid())
                .setMidTime(DateUtils.date2String(stayPoint.getMidTime()))
                .setDuration(stayPoint.getDuration()/60)
                .setLng(stayPoint.getLng())
                .setLat(stayPoint.getLat());
        return stayPointVo;
    }

    private List<Double> object2Double(List<Group> groups, int size) {
        List<Double> res = new ArrayList<>();
        int count = 0;
        for (Group group : groups) {
            res.add(group.getValue());
            count ++;
        }
        for(int i = count; i < size; i ++) {
            res.add(0.0);
        }
        return res;
    }

    /**
     * poi类型转换
     * @param poiList
     * @return
     */
    private List<PoiVo> poiTransfer(List<Poi> poiList) {
         return poiList.parallelStream()
                .collect(Collectors.groupingBy(Poi::getPoiType,TreeMap::new, Collectors.toList()))
                .entrySet()
                .parallelStream()
                .map(
                        e->{
                            PoiVo poiVo = new PoiVo();
                            poiVo.setPoiType(e.getKey());
                            List<PoiInfo> poiInfos = e.getValue().parallelStream()
                                    .map(
                                            poi -> {
                                                PoiInfo poiInfo = new PoiInfo();
                                                poiInfo.setPoiName(poi.getPoiName())
                                                        .setCount(poi.getCount())
                                                        .setLng(poi.getLng())
                                                        .setLat(poi.getLat());
                                                return poiInfo;
                                            }

                                    )
                                    .collect(Collectors.toList());
                            poiVo.setPoiInfos(poiInfos);
                           return poiVo;
                        }
                ).collect(Collectors.toList());
    }
}
