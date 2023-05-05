package com.example.demo5.controller;

import com.example.demo5.common.ResponseResult;
import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.ChartData3;
import com.example.demo5.entity.PlaceInfo;
import com.example.demo5.entity.vo.CandidatePlaceVo;
import com.example.demo5.service.PlaceAllService;
import com.example.demo5.service.PlaceStatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 地点筛选接口
 *
 * @author huyue87@jd.com
 * @date 2020/12/16 18:15
 * @since 2.0.0
 */
@RestController
@Api
@CrossOrigin
public class PlaceSelectionController {

    @Autowired
    PlaceStatService placeStatService;
    @Autowired
    PlaceAllService placeAllService;

    @ApiOperation("地点筛选统计信息")
    @PostMapping("/getinfo")
    public ResponseResult<PlaceInfo> generatePlace(
            @RequestBody CandidatePlaceVo... candidatePlaceVos
            ) throws IOException, ParseException, ExecutionException, InterruptedException {
//        List<CandidatePlaceVo> candidatePlaceVos = new ArrayList<>();
//        List<CandidatePlaceVo> candidatePlaceVos = new ArrayList<>();
//        JSONArray jsonArray = jsonObject.getJSONArray("placelist");
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject o = jsonArray.getJSONObject(i);
//            String jsonStr = JSON.toJSONString(o);
//            candidatePlaceVos.add(JSON.parseObject(jsonStr, CandidatePlaceVo.class));
////            candidatePlaceVos.add(JSON.parseObject(jsonStr, CandidatePlaceVo.class));
//        }
        PlaceInfo placeInfo = new PlaceInfo();
        List<CandidatePlaceVo> candidatePlaceVoList = Arrays.asList(candidatePlaceVos);
        List<CandidatePlace> candidatePlaces =candidatePlaceVoList.stream()
                .map(this::placeTransfer1)
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

        placeInfo.setCandidatePlaceVos(candidatePlaceVoList)
                .setPlaceNum(candidatePlaces.size())
                .setChartData(chartData);
        return new ResponseResult(200, "统计成功", placeInfo);
    }

    /**
     * CandidatePlaceVo -> CandidatePlace
     * @param
     * @return
     */
    private CandidatePlace placeTransfer1(CandidatePlaceVo candidatePlaceVo) {
        CandidatePlace candidatePlace = new CandidatePlace();

        candidatePlace.setLng(candidatePlaceVo.getLng())
                .setLat(candidatePlaceVo.getLat())
                .setVisitSum(candidatePlaceVo.getVisitSum())
                .setVisitObj(candidatePlaceVo.getVisitObj())
                .setDurationAvg(candidatePlaceVo.getDurationAvg())
                .setRoad1len(candidatePlaceVo.getRoad1len())
                .setRoad2len(candidatePlaceVo.getRoad2len())
                .setRoad3len(candidatePlaceVo.getRoad3len())
                .setIntersection(candidatePlaceVo.getIntersection())
                .setFreqMean(candidatePlaceVo.getFreqMean())
                .setTimeSegment(candidatePlaceVo.getTimeSegment());
        return candidatePlace;
//        // chartdata 渲染统计数据
//        ChartData2 chartData = new ChartData2();
//        chartData.setDurationDistribute(candidatePlace.getDurationDistribute())
//                .setPoiDistribute(candidatePlace.getPoiDistribute())
//                .setFreqDistribute(candidatePlace.getFreqDistribute())
//                .setTimeDistribute(candidatePlace.getTimeDistribute())
//                .setWeekDistribute(candidatePlace.getWeekDistribute());
//
//        candidatePlaceVo.setLng(candidatePlace.getLng())
//                .setLat(candidatePlace.getLat())
//                .setVisitSum(candidatePlace.getVisitSum())
//                .setVisitObj(candidatePlace.getVisitObj())
//                .setDurationAvg(candidatePlace.getDurationAvg())
//                .setRoad1len(candidatePlace.getRoad1len())
//                .setRoad2len(candidatePlace.getRoad2len())
//                .setRoad3len(candidatePlace.getRoad3len())
//                .setFreqMean(candidatePlace.getFreqMean())
//                .setTimeSegment(candidatePlace.getTimeSegment())
//                .setConcaveHull(candidatePlace.getPolygon())
//                .setIntersection(candidatePlace.getIntersection())
//                .setChartData(chartData);
//
//        return candidatePlaceVo;
    }
}
