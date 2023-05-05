package com.example.demo5;

import com.example.demo5.entity.*;
import com.example.demo5.entity.vo.StayPointVo;
import com.example.demo5.mapper.StayPointMapper;
import com.example.demo5.service.GeoGenerateService;
import com.example.demo5.service.PlaceAllService;
import com.example.demo5.service.PlaceStatService;
import com.example.demo5.service.StayPointService;
import com.example.demo5.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
public class searchTest                                                                                  {
    @Autowired
    private StayPointMapper stayPointMapper;
    @Autowired
    private StayPointService stayPointService;
//    @Autowired
//    private PoiService poiService;
    @Autowired
    private GeoGenerateService geoGenerateService;
    @Autowired
    private PlaceStatService placeStatService;
    @Autowired
    private PlaceAllService placeAllService;
    @Test
    void test() throws IOException, ParseException {

        String sTime = "2008-02-02 00:00:00";
        String eTime = "2008-02-04 00:00:00";
        String mbrString = new Mbr(116.24368, 116.35368, 39.96596, 39.97472).toString();
        List<String> Ptype = new ArrayList<>();
        Ptype.add("加油站");
        Ptype.add("住宅区");
        List<Integer> Rtype = new ArrayList<>();
        Rtype.add(1);
        Rtype.add(2);

        FilterConditions filterConditions = new FilterConditions();
//        filterConditions.setDurationSymbol("<=");
//        filterConditions.setDuration(20.0);
        filterConditions.setTableName("tdrive_15_50");
//        filterConditions.setRegionStr("大兴区");
        filterConditions.setDurationSymbol("<");
        filterConditions.setDuration(30.0);
        List<StayPointVo> stayPointVos = stayPointService.searchFilterPoint(filterConditions);
        List<StayPoint> stayPointList = stayPointVos.stream()
                .map(this::transfer)
                .collect(Collectors.toList());
        List<CandidatePlace> candidatePlaceList = geoGenerateService.geoGenerate(stayPointList, 15, 0.001);
//        PlaceInfo placeInfo = placeStatService.getPlaceInfo(candidatePlaceList);
        System.out.println("候选地点个数" + candidatePlaceList.size());

        System.out.println("------------------------------------------------------");

        BufferedWriter out = new BufferedWriter(new FileWriter("D:\\polygon.txt"));
//        System.out.println("集群号：" + candidatePlaceList.get(0).getStayPointList().get(0).getClusterID());
        for (CandidatePlace candidatePlace: candidatePlaceList) {
            placeStatService.featureStillExtract(candidatePlace, 0.01);
            placeStatService.featureDynamicExtract(candidatePlace);
            System.out.println("经度:" + candidatePlace.getLng());
            System.out.println("纬度:" + candidatePlace.getLat());
            System.out.println("总访问数:" + candidatePlace.getVisitSum());
            System.out.println("总访问对象数:" + candidatePlace.getVisitObj());
            System.out.println("平均访问时长:" + candidatePlace.getDurationAvg());
            System.out.println("最常访问时间段:" + candidatePlace.getTimeSegment());
            System.out.println("平均访问频次:" + candidatePlace.getFreqMean());
            System.out.println("交叉路口数:" + candidatePlace.getIntersection());
            System.out.println("poi分布");
//            printMap(candidatePlace.getPoiDistribute());
            System.out.println("访问频次分布");
            printMap(candidatePlace.getFreqDistribute());
            System.out.println("访问时间分布");
            printMap(candidatePlace.getTimeDistribute());
            System.out.println("访问时长分布");
            printMap(candidatePlace.getDurationDistribute());
            System.out.println("路段1长度" + candidatePlace.getRoad1len());
            System.out.println("路段2长度" + candidatePlace.getRoad2len());
            System.out.println("路段3长度" + candidatePlace.getRoad3len());


            System.out.println("闭包:" + candidatePlace.getPolygon());
            // 把闭包写入文件测试
            String tmp = candidatePlace.getPolygon() + "\n";
            out.write(tmp);

        }
        System.out.println("--------------------------------------");
        System.out.println("------------地点的整体分布-------------------");
        System.out.println("平均访问时长分布");
        printMap(placeAllService.durationAvgStat(candidatePlaceList));
        System.out.println("平均访问频次分布");
        printMap(placeAllService.freqMeanStat(candidatePlaceList));
        System.out.println("时间段分布");
        printMap(placeAllService.timeSegStat(candidatePlaceList));
    }

    private StayPoint transfer(StayPointVo stayPointVo) {
        StayPoint stayPoint = new StayPoint();
        stayPoint.setTid(stayPointVo.getTid())
                .setOid(stayPointVo.getOid())
                .setDuration(stayPointVo.getDuration())
                .setLng(stayPointVo.getLng())
                .setLat(stayPointVo.getLat())
                .setMidTime(DateUtils.string2Date(stayPointVo.getMidTime()))
                .setCentroid("Point(" + stayPointVo.getLng() + " " + stayPointVo.getLat() + ")")
                .setClusterID(0);
        return stayPoint;
    }

    private void printMap(List<Group> groups) {
        for (Group group : groups) {
            System.out.println(group.getName() + " " + group.getValue());
        }

    }

}





