package com.example.demo5;

import com.example.demo5.entity.*;
import com.example.demo5.entity.vo.StayPointVo;
import com.example.demo5.mapper.PoiMapper;
import com.example.demo5.service.ExistsPlaceService;
import com.example.demo5.service.GeoGenerateService;
import com.example.demo5.service.PlaceStatService;
import com.example.demo5.service.StayPointService;
import com.example.demo5.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 用于测试空间范围查询
 *
 * @author huyue87@jd.com
 * @date 2020/12/11 10:20
 * @since 2.0.0
 */
@SpringBootTest
public class spatialSearchTest {

    @Autowired
    PoiMapper poiMapper;

    @Autowired
    StayPointService stayPointService;

    @Autowired
    GeoGenerateService geoGenerateService;

    @Autowired
    ExistsPlaceService existsPlaceService;

    @Autowired
    PlaceStatService placeStatService;

    @Test
    void test() throws IOException, ParseException, ExecutionException, InterruptedException {
        List<Poi> poiList = poiMapper.selectAllTest();
        System.out.println(poiList.size());

        // 用于地点生成
        FilterConditions filterConditions = new FilterConditions();
//        filterConditions.setDurationSymbol("<=");
//        filterConditions.setDuration(20.0);
        filterConditions.setTableName("tdrive_15_50");
//        filterConditions.setRegionStr("大兴区");
        filterConditions.setDurationSymbol("<");
        filterConditions.setDuration(22.0);
        List<StayPointVo> stayPointVos = stayPointService.searchFilterPoint(filterConditions);
        List<StayPoint> stayPointList = stayPointVos.stream()
                .map(this::transfer)
                .collect(Collectors.toList());
        System.out.println("驻留点数目" + stayPointList.size());
        List<CandidatePlace> candidatePlaceList = existsPlaceService.existPlace(stayPointList, "university");
//        List<CandidatePlace> candidatePlaceList = geoGenerateService.geoGenerate(stayPointList, 10, 0.04);
//        PlaceInfo placeInfo = placeStatService.getPlaceInfo(candidatePlaceList);
        System.out.println("候选地点个数" + candidatePlaceList.size());
        PlaceInfo placeInfo = placeStatService.getPlaceInfo(candidatePlaceList);


//        for (CandidatePlace candidatePlace : candidatePlaceList) {
//            Geometry geometry = GeoUtils.wkt2Geometry(GeoUtils.getCenter(candidatePlace.getStayPointList()).toString());
//            SpatialPoiSearch spatialPoiSearch = new SpatialPoiSearch(poiList);
//            long startTime1 = System.currentTimeMillis();    //获取开始时间
//
//            List<Poi> res = spatialPoiSearch.getTargetList(geometry, GeoUtils.km2Degree(0.5));
//            System.out.println("size1:" + res.size());
//            long endTime1 = System.currentTimeMillis();    //获取开始时间
//
//            System.out.println("内存集合运算程序运行时间1：" + (endTime1 - startTime1) + "ms");    //输出程序运行时间
//
//            // 空间范围查询 数据库
//            GeoPoint geoPoint = GeoUtils.getCenter(candidatePlace.getStayPointList());
//            // 候选地点的中心坐标
//            candidatePlace.setLng(geoPoint.getLng())
//                    .setLat(geoPoint.getLat());
//            // 地点查询范围
//            Mbr mbr = new Mbr(geoPoint.getLng() - 0.004, geoPoint.getLat() + 0.004,
//                    geoPoint.getLat() - 0.004, geoPoint.getLat() + 0.004);
//
//            System.out.println("方框查询：" + mbr.toString());
//
//            System.out.println("中心点坐标：" + geoPoint.toString());
//
//            long startTime2 = System.currentTimeMillis();    //获取开始时间
//
//            List<Poi> res2 = poiMapper.selectSpatialTest(geoPoint.toString());
//            System.out.println("size2:" + res2.size());
//
//            long endTime2 = System.currentTimeMillis();    //获取开始时间
//
//            System.out.println("数据库运算程序运行时间2：" + (endTime2 - startTime2) + "ms");    //输出程序运行时间
//
//        }
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
}
