package com.example.demo5.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo5.common.ResponseResult;
import com.example.demo5.entity.FilterConditionV2;
import com.example.demo5.entity.FilterConditions;
import com.example.demo5.entity.StayPointInfo;
import com.example.demo5.entity.condition.*;
import com.example.demo5.service.GeoGenerateService;
import com.example.demo5.service.PlaceStatService;
import com.example.demo5.service.PointStatService;
import com.example.demo5.service.StayPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-14 11:38 2020-11-14 11:48
 * 驻留点、poi、路网筛选控制层
 */
@Api(tags = "驻留点筛选和数据统计结果")
@RestController
//@CrossOrigin
public class SelectController {
    @Autowired
    private StayPointService stayPointService;
    @Autowired
    private PointStatService pointStatService;
    @Autowired
    private PlaceStatService placeStatService;
    @Autowired
    private GeoGenerateService geoGenerateService;

    /**
     * 驻留点的筛选
     */
    @ApiOperation("驻留点的多条件筛选")
    @PostMapping("/selectstaypoint")
    public ResponseResult<StayPointInfo> selectStayPoint(@RequestBody FilterConditions filterConditions) {
        StayPointInfo stayPointInfo = stayPointService.searchFilter(filterConditions);
        if(stayPointInfo.getStayPointVos().size() == 0) {
            return new ResponseResult(200, "查询结果为空");
        }
        return new ResponseResult(200, stayPointInfo);
    }

    /**
     * 驻留点的筛选
     */
    @ApiOperation("驻留点的多条件筛选V2")
    @PostMapping("/selectstaypointV2")
    public ResponseResult<StayPointInfo> selectStayPointV2(@RequestBody FilterConditionV2 filterConditionV2) {
        StayPointInfo stayPointInfo = stayPointService.searchFilterV2(filterConditionV2);
        if(stayPointInfo.getStayPointVos().size() == 0) {
            return new ResponseResult(200, "查询结果为空");
        }
        return new ResponseResult(200, "筛选成功", stayPointInfo);
    }

    /**驻留点的筛选
     * 驻留点的筛选
     */
    @ApiOperation("驻留点的多条件筛选V3")
    @PostMapping("/selectstaypointV3")
    public ResponseResult<StayPointInfo> selectStayPointV3(@RequestBody JSONObject jsonObject) {
        long startTime1 = System.currentTimeMillis();    //获取开始时间
        FilterConditionV2 filterConditionV2 = new FilterConditionV2();
        // 提取出表名
        String tableName = jsonObject.getString("tableName");
        filterConditionV2.setTableName(tableName);
        // 提取出条件列表
        JSONArray jsonArray = jsonObject.getJSONArray("conditionList");
        System.out.println(jsonArray);
//        if(jsonArray.size() == 0) {
//            StayPointInfo stayPointInfo = stayPointService.searchFilterV2(filterConditionV2);
//            return new ResponseResult<>(200, "筛选成功", stayPointInfo);
//        }
        List<BaseCondition> baseConditionList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject tmp = jsonArray.getJSONObject(i);
            System.out.println(tmp);
            BaseCondition baseCondition = jsonObject2Con(tmp);
            baseConditionList.add(jsonObject2Con(tmp));
        }

        filterConditionV2.setConditionList(baseConditionList);
        long endTime1 = System.currentTimeMillis();    //获取结束
        System.out.println("json extract time:" + (endTime1 - startTime1) + "ms");
        StayPointInfo stayPointInfo = stayPointService.searchFilterV2(filterConditionV2);
//        System.out.println(stayPointInfo.getPoiNameTop());
//        long endTime1 = System.currentTimeMillis();
//        System.out.println("select total time:" + (endTime1 - startTime1) + "ms");
        if(stayPointInfo.getStayPointVos().size() == 0) {
            return new ResponseResult(200, "查询结果为空");
        }
        return new ResponseResult<>(200, "筛选成功", stayPointInfo);
    }

    /**
     * JSONObject 转为Condition对象
     * @param jsonObject jsonobject
     * @return
     */
    private BaseCondition jsonObject2Con(JSONObject jsonObject) {
        String flag = jsonObject.getString("type");
        String jsonStr = JSON.toJSONString(jsonObject);
        BaseCondition baseCondition = new BaseCondition();
        baseCondition.setType(flag);
        if (flag.equals("poi")) {
            baseCondition = JSONObject.toJavaObject(jsonObject, PoiCondition.class);
        } else if (flag.equals("road")) {
            baseCondition = JSON.parseObject(jsonStr, RoadCondition.class);
        } else if (flag.equals("intersect")) {
            baseCondition = JSON.parseObject(jsonStr, IntersectCondition.class);
        } else if (flag.equals("duration")) {
            baseCondition = JSON.parseObject(jsonStr, DurationCondition.class);
        } else if (flag.equals("spatialscope")) {
            baseCondition = JSON.parseObject(jsonStr, SpatialScopeCondition.class);
        } else if (flag.equals("timescope")) {
            baseCondition = JSON.parseObject(jsonStr, TimeScopeCondition.class);
        } else if (flag.equals("timeseg")) {
            baseCondition = JSON.parseObject(jsonStr, TimeSegCondition.class);
        } else if(flag.equals("week")) {
            baseCondition = JSON.parseObject(jsonStr, WeekCondition.class);
        } else if(flag.equals("region")) {
            baseCondition = JSON.parseObject(jsonStr, RegionCondition.class);
        } else if (flag.equals("oftenstay")) {
            baseCondition = JSONObject.toJavaObject(jsonObject, StayPlaceCondition.class);
        }
        baseCondition.setType(flag);
        return baseCondition;
    }
}
