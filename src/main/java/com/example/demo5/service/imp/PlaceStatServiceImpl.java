package com.example.demo5.service.imp;

import com.example.demo5.common.GeoPoint;
import com.example.demo5.entity.*;
import com.example.demo5.entity.vo.CandidatePlaceVo;
import com.example.demo5.mapper.IntersectMapper;
import com.example.demo5.mapper.PoiMapper;
import com.example.demo5.mapper.RoadDataMapper;
import com.example.demo5.service.*;
import com.example.demo5.util.DateUtils;
import com.example.demo5.util.NumUtils;
import com.example.demo5.util.StatisticUtils;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-16 10:05
 * 候选地点统计功能接口的实现
 */
@Service
public class PlaceStatServiceImpl implements PlaceStatService {
    /**
     * 引入路段
     */
    @Autowired
    RoadDataService roadDataService;
    /**
     * 引入poi
     */
    @Autowired
    PoiService poiService;

    @Autowired
    PoiMapper poiMapper;
    /**
     * 引入交叉路口
     */
    @Autowired
    IntersectMapper intersectMapper;

    /**
     * 地点全量统计信息
     */
    @Autowired
    PlaceAllService placeAllService;

    @Autowired
    PlaceAsyncService placeAsyncService;

    @Autowired
    RoadDataMapper roadDataMapper;

    private boolean isBeijingPoint(GeoPoint point) {
        if (point.getLat() > 39 && point.getLat() < 42) {
            return true;
        }
        return false;
    }

    /**
     * 静态特征： 包括候选地点中心坐标、poi分布、路网分布、交叉路口数目
     */
    @Override
    public void featureStillExtract(CandidatePlace candidatePlace, double radius) throws ParseException {
//        // 统计路网信息 质心坐标另算
//        GeoPoint geoPoint = GeoUtils.getCenter(candidatePlace.getStayPointList());
//        // 候选地点的中心坐标
//        candidatePlace.setLng(geoPoint.getLng())
//                .setLat(geoPoint.getLat());

        GeoPoint geoPoint = new GeoPoint(candidatePlace.getLng(), candidatePlace.getLat());
////         地点查询范围
//        Mbr mbr = new Mbr(geoPoint.getLng() - radius, geoPoint.getLat() + radius,
//                geoPoint.getLat() - radius, geoPoint.getLat() + radius);
//
////         路段统计查询
//        List<RoadData> roadData = roadDataService.searchSpatial(mbr.toString());
        String roadTable = "road_net_beijing";
        String poiTable = "poi_beijing";
        if (!isBeijingPoint(geoPoint)) {
            roadTable = "road_net_nantong";
            poiTable = "poi_nantong";
        }
        List<Group> roadData = roadDataMapper.selectSpatialTest(geoPoint.toString(), roadTable);
        for (Group group : roadData) {
//            System.out.println(group);
            if (group.getName().equals("1")) {
                candidatePlace.setRoad1len(group.getValue());
            } else if (group.getName().equals("2")) {
                candidatePlace.setRoad2len(group.getValue());
            } else if (group.getName().equals("3")) {
                candidatePlace.setRoad3len(group.getValue());
            }
        }

        // 交叉路口
        Integer intersect = intersectMapper.selectSpatialTest(geoPoint.toString());
        candidatePlace.setIntersection(intersect);
        System.out.println("候选地点100m范围内交叉路口数" + candidatePlace.getIntersection());

        // poi列表信息
//        List<Poi> poiList = poiService.searchPoiSpatial(mbr.toString());
        List<Poi> poiList = poiMapper.selectSpatialTest(geoPoint.toString(), poiTable);

        candidatePlace.setPoiList(poiList);

        // poi统计信息
        candidatePlace.setPoiDistribute(poiFeatureExtract(poiList));
    }

    @Override
    public void featureDynamicExtract(CandidatePlace candidatePlace) {

        List<StayPoint> stayPoints = candidatePlace.getStayPointList();
        // 访问时长字段
        List<Double> duration = stayPoints.stream()
                .map(StayPoint::getDuration)
                .collect(Collectors.toList());

        // 访问时间字段
        List<Date> dates = stayPoints.parallelStream()
                .map(StayPoint::getMidTime)
                .collect(Collectors.toList());

        // 总访问数
        candidatePlace.setVisitSum(stayPoints.size());

        // 给每个poi增加访问次数
//        List<Poi> poiList = candidatePlace.getPoiList();
//        for (Poi poi : poiList) {
//            poi.setVisitnum(candidatePlace.getVisitSum());
//        }

        // 访问时长分布
        List<Group> tmp = StatisticUtils.durationStat(duration);
        candidatePlace.setDurationDistribute(tmp);

        // 地点平均访问时长
        candidatePlace.setDurationAvg(mediumDurationExtract(duration));


        // 不同对象的访问数 -> 对驻留点对象的distinct(java 8 对某一字段进行去重)
        List<StayPoint> stayPoints1 = stayPoints.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(StayPoint::getOid))
                ), ArrayList::new));
        candidatePlace.setVisitObj(stayPoints1.size());

        List<Group> timeD = StatisticUtils.timeSegStat(dates);

        // 访问时间段分布
        candidatePlace.setTimeDistribute(timeD);

        // 最常访问时间段
        candidatePlace.setTimeSegment(mostOftenExtract(timeD));


        // 访问时间周分布
        candidatePlace.setWeekDistribute(StatisticUtils.weekDayStat(dates
                .parallelStream()
                .map(DateUtils::getWeekDay)
                .collect(Collectors.toList())));

        // 获得每个对象的访问频次
        Map<String, Double> tmpFreq = freqVisitExtract(stayPoints);
        // 平均访问频次
        candidatePlace.setFreqMean(freqMeanStat(tmpFreq));

        // 访问频次分布（对每个对象的访问频次分布）
        // 将map里的每一个访问频次值取出
        candidatePlace.setFreqDistribute(StatisticUtils.freqStat(NumUtils.map2List(tmpFreq)));

        // 访问年份分布
        candidatePlace.setYearDistribute(StatisticUtils.yearStat(dates));
        // 访问月份分布
        candidatePlace.setMonthDistribution(StatisticUtils.monthStat(dates));
    }

    @Override
//     线程数
    public List<CandidatePlace> getSingleFeature(List<CandidatePlace> candidatePlaces) throws ParseException, ExecutionException, InterruptedException {
        List<Future<CandidatePlace>> futures = new ArrayList<>();
        for (int i = 0; i < candidatePlaces.size(); i++) {
            Future<CandidatePlace> future = placeAsyncService.executeAsync(candidatePlaces.get(i));
            futures.add(future);
        }
        List<CandidatePlace> candidatePlaces1 = new ArrayList<>();
        Iterator<Future<CandidatePlace>> it = futures.iterator();
//        for (Future future : futures) {
//            candidatePlaces1.add((CandidatePlace) future.get());
//        }
        while (it.hasNext()) {
            Future<CandidatePlace> x = it.next();
            if (x != null) {
                candidatePlaces1.add(x.get());
            }
        }
        return candidatePlaces1;
    }

    @Override
    public PlaceInfo getPlaceInfo(List<CandidatePlace> candidatePlaces) throws ParseException, ExecutionException, InterruptedException {
        PlaceInfo placeInfo = new PlaceInfo();
        // 提取单个地点特征 for循环
        /*********************** for循环提取信息 *******************/
//        for (CandidatePlace candidatePlace : candidatePlaces) {
//          featureStillExtract(candidatePlace, 0.01);
//          featureDynamicExtract(candidatePlace);
//        }
        /********************* 多线程提取信息 **********************/
        getSingleFeature(candidatePlaces);
        // CandidatePlace -> CandidatePlaceVo
        List<CandidatePlaceVo> candidatePlaceVos = candidatePlaces.stream()
                .map(this::placeTransfer)
                .collect(Collectors.toList());
        ChartData3 chartData = new ChartData3();
        chartData.setVisitSumDistribute(placeAllService.visitSumStat(candidatePlaces))
                .setFreqMeanDistribute(placeAllService.freqMeanStat(candidatePlaces))
                .setVisitObjStatDistribute(placeAllService.visitObjStat(candidatePlaces))
                .setDurationAvgDistribute(placeAllService.durationAvgStat(candidatePlaces))
                .setIntersectDistribute(placeAllService.intersectStat(candidatePlaces))
                .setPoiTypeDistribute(placeAllService.poiTypeStat(candidatePlaces))
                .setTimeSegDistribute(placeAllService.timeSegStat(candidatePlaces))
                .setRoad1lenDistribute(placeAllService.road1lenStat(candidatePlaces))
                .setRoad2lenDistribute(placeAllService.road2lenStat(candidatePlaces))
                .setRoad3lenDistribute(placeAllService.road3lenStat(candidatePlaces));

        placeInfo.setCandidatePlaceVos(candidatePlaceVos)
                .setPlaceNum(candidatePlaceVos.size())
                .setChartData(chartData);

        return placeInfo;
    }


    /**
     * CandidatePlace -> CandidatePlaceVo
     *
     * @param candidatePlace
     * @return
     */
    private CandidatePlaceVo placeTransfer(CandidatePlace candidatePlace) {
        CandidatePlaceVo candidatePlaceVo = new CandidatePlaceVo();
        // chartdata 渲染统计数据
        ChartData2 chartData = new ChartData2();
        chartData.setDurationDistribute(candidatePlace.getDurationDistribute())
                .setPoiDistribute(candidatePlace.getPoiDistribute())
                .setFreqDistribute(candidatePlace.getFreqDistribute())
                .setTimeDistribute(candidatePlace.getTimeDistribute())
                .setWeekDistribute(candidatePlace.getWeekDistribute())
                .setYearDistribute(candidatePlace.getYearDistribute())
                .setMonthDistribute(candidatePlace.getMonthDistribution());

        candidatePlaceVo.setLng(candidatePlace.getLng())
                .setLat(candidatePlace.getLat())
                .setName(candidatePlace.getName())
                .setVisitSum(candidatePlace.getVisitSum())
                .setVisitObj(candidatePlace.getVisitObj())
                .setDurationAvg(candidatePlace.getDurationAvg())
                .setRoad1len(candidatePlace.getRoad1len())
                .setRoad2len(candidatePlace.getRoad2len())
                .setRoad3len(candidatePlace.getRoad3len())
                .setFreqMean(candidatePlace.getFreqMean())
                .setTimeSegment(candidatePlace.getTimeSegment())
                .setConcaveHull(candidatePlace.getPolygon())
                .setIntersection(candidatePlace.getIntersection())
                .setChartData(chartData);

        return candidatePlaceVo;
    }

    /**
     * 提取poi特征
     */
    private List<Map<String, Object>> poiFeatureExtract(List<Poi> poiList) {
        return poiList.parallelStream()
                .collect(Collectors.groupingBy(Poi::getPoiType, TreeMap::new, Collectors.toList()))
                .entrySet()
                .parallelStream()
                .map(
                        e -> {
                            Map<String, Object> item = new TreeMap<>();
                            double prop = (double) e.getValue().size() / poiList.size();
                            item.put("name", e.getKey());
                            item.put("value", prop);
                            return item;
                        }
                ).collect(Collectors.toList());
    }

    /**
     * 提取时长中位数
     */
    private Double mediumDurationExtract(List<Double> duration) {
        // 对时长排序
        Collections.sort(duration);
        double medium = 0.0;
        // 访问时长中位数
        if (duration.size() % 2 == 0) {
            medium = (duration.get(duration.size() / 2 - 1) + duration.get(duration.size() / 2)) / 2;
        } else {
            medium = duration.get(duration.size() / 2);
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        String tempM = nf.format(medium);
        tempM = tempM.replaceAll(",", "");
        return Double.parseDouble(tempM);
    }

    /**
     * 访问频次(map 对象-> 单个对象访问频次)
     */
    private Map<String, Double> freqVisitExtract(List<StayPoint> stayPointList) {
        Map<String, Double> res = new HashMap<>();
        // 对oid进行分组
        Map<String, List<StayPoint>> stayPointGroup = stayPointList.parallelStream()
                .collect(Collectors.groupingBy(StayPoint::getOid));
        // 遍历map
        for (Map.Entry<String, List<StayPoint>> entry : stayPointGroup.entrySet()) {
            if (entry.getValue().size() >= 2) {
                Double freqSum = freqForSingle(entry.getValue()
                        .parallelStream()
                        .map(StayPoint::getMidTime)
                        .collect(Collectors.toList()));
                res.put(entry.getKey(), freqSum);
            }
        }
        return res;
    }

    /**
     * 计算单个对象的访问频次
     */
    private Double freqForSingle(List<Date> dates) {
        // 按照date排序
        Collections.sort(dates);
        Double res = 0.0;
        for (int i = 0; i < dates.size() - 1; i++) {
            res += DateUtils.timeDiff(dates.get(i), dates.get(i + 1));
        }
        return (-1) * (res / dates.size() - 1);
    }

    /**
     * 返回map中值最大的key 用于最经常访问时间段
     */
    private String mostOftenExtract(List<Group> groups) {
        Double maxVisit = 0.0;
        String res = null;
        for (Group group : groups) {
            if (group.getValue() > maxVisit) {
                res = group.getName();
                maxVisit = group.getValue();
            }
        }
        return res;
    }

    /**
     * 获得平均访问频次
     */
    private Double freqMeanStat(Map<String, Double> pairs) {
        // 平均访问频次
        Integer count = 0;
        Double freqSum = 0.0;
        for (Map.Entry<String, Double> entry : pairs.entrySet()) {
            count++;
            freqSum += entry.getValue();
        }
        if (count == 0) {
            return 0.0;
        } else {
            return count / freqSum;
        }
    }

}


