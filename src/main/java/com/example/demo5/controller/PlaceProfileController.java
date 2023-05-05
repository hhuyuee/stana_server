package com.example.demo5.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo5.common.ResponseResult;
import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.PlaceInfo;
import com.example.demo5.entity.StayPoint;
import com.example.demo5.entity.vo.StayPointVo;
import com.example.demo5.service.*;
import com.example.demo5.util.GeoUtils;
import com.example.demo5.util.PoiNameMapperUtils;
import io.swagger.annotations.Api;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 地点画像生成接口
 *
 * @author huyue87@jd.com
 * @date 2021/3/30 16:14
 * @since 2.0.0
 */
@RestController
@Api
@CrossOrigin
public class PlaceProfileController {
    @Autowired
    PlaceStatService placeStatService;
    @Autowired
    PlaceAllService placeAllService;
    @Autowired
    GeoGenerateService geoGenerateService;
    @Autowired
    GeoGenerateService1 geoGenerateService1;
    @Autowired
    ExistsPlaceService existsPlaceService;

    @PostMapping("/getprofile")
    public ResponseResult<PlaceInfo> generatePlace(
            @RequestBody JSONObject jsonObject
    ) throws ParseException, ExecutionException, InterruptedException, IOException {
        // 地点选择模式类型(mode:exists; generated)
        String mode = jsonObject.getString("mode");
        JSONArray jsonArray = jsonObject.getJSONArray("stayPoints");
        List<StayPoint> stayPointList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject tmp = jsonArray.getJSONObject(i);
            StayPoint stayPoint = JSONObject.toJavaObject(tmp, StayPoint.class);
            stayPoint.setCentroid("Point(" + stayPoint.getLng() + " " + stayPoint.getLat() + ")");
            stayPointList.add(stayPoint);
        }
        List<CandidatePlace> candidatePlaces = null;
        if (mode.equals("exists")) {
            // 地点类型
            System.out.println("111");
            String type = jsonObject.getString("type");
            System.out.println(type);
//            if (type.equals("学校")) {
//                type = "university";
//            }
            type = PoiNameMapperUtils.getAoiName(type);
            candidatePlaces = existsPlaceService.existPlace(stayPointList, type);

        } else if (mode.equals("generate")) {
            long startTime1 = System.currentTimeMillis();    //获取开始时间
            Integer minpts = jsonObject.getInteger("minpts");
            Double eps = jsonObject.getDouble("eps");
            // 解析前台传递的"stayPoints"json 映射到List<StayPoint>
            long endTime1 = System.currentTimeMillis();
            System.out.println("json extract" + (endTime1 - startTime1) + "ms");    //输出程序运行时间
            // 将eps(米)转换为(度)
            Double degree = GeoUtils.km2Degree(eps / 1000);
            System.out.println("degree" + degree);
            // 生成候选地点
            long startTime2 = System.currentTimeMillis();
            // 采样
//        if(stayPointList.size() < 10000) {
            candidatePlaces = geoGenerateService.geoGenerate(stayPointList, minpts, degree);
//        } else {
//            candidatePlaces = geoGenerateService1.geoGenerate1(stayPointList, minpts, degree);
//        }
            long endTime2 = System.currentTimeMillis();
            System.out.println("place generate" + (endTime2 - startTime2) + "ms");    //输出程序运行时间
        }
        long startTime3 = System.currentTimeMillis();
        // 返回候选地点信息 (placeinfo)
        PlaceInfo placeInfo = placeStatService.getPlaceInfo(candidatePlaces);
        long endTime3 = System.currentTimeMillis();
        System.out.println("place statistic" + (endTime3 - startTime3) + "ms");    //输出程序运行时间
        return new ResponseResult(200, "地点画像生成成功", placeInfo);
    }

    /**
     * staypointvo 和 staypoint的转换
     */
    private StayPoint transfer(StayPointVo stayPointVo) {
        StayPoint stayPoint = new StayPoint();

        stayPoint.setTid(stayPointVo.getTid())
                .setOid(stayPointVo.getOid())
                .setDuration(stayPointVo.getDuration())
                .setLng(stayPointVo.getLng())
                .setLat(stayPointVo.getLat())
                .setCentroid("Point(" + stayPointVo.getLng() + " " + stayPointVo.getLat() + ")")
                .setClusterID(0);
        return stayPoint;
    }
}
