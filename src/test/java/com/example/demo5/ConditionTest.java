package com.example.demo5;

import com.example.demo5.mapper.StayPointMapper;
import com.example.demo5.service.StayPointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * condition test
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 15:21
 * @since 2.0.0
 */
@SpringBootTest
public class ConditionTest {
    @Autowired
    StayPointMapper stayPointMapper;
    @Autowired
    StayPointService stayPointService;
    @Test
    void test() {

//        PoiCondition poiCondition = new PoiCondition("poi");
//        PoiParameter poiParameter = new PoiParameter("加油站#超市商场", 100.0, 1);
//        poiCondition.setData(poiParameter);
//        System.out.println(poiCondition.buildSql());
//
//        RoadCondition roadCondition = new RoadCondition("road");
//        RoadParameter roadParameter = new RoadParameter("1#2",100.0,1);
//        roadCondition.setData(roadParameter);
//
//        List<BaseCondition> baseConditionList = new ArrayList<>();
//        baseConditionList.add(poiCondition);
//        baseConditionList.add(roadCondition);
//
//        for (BaseCondition baseCondition : baseConditionList) {
//            if (baseCondition.getType() == "poi") {
//                System.out.println(baseCondition.buildSql());
//            }
//
//        }

//        System.out.println(baseConditionList);
//        FilterConditionV2 filterConditionV2 = new FilterConditionV2();
//        PoiCondition poiCondition = new PoiCondition("poi", "加油站#超市商场", 100.0, 0);
////        RoadCondition roadCondition = new RoadCondition("roadlevel", "1#2", 100.0 ,1);
////        RegionCondition regionCondition = new RegionCondition("region","1#2");
//        List<BaseCondition> baseConditionList = new ArrayList<>();
//        baseConditionList.add(poiCondition);
////        baseConditionList.add(roadCondition);
////        baseConditionList.add(regionCondition);
//        StringBuilder sql = new StringBuilder();
//        for (BaseCondition baseCondition : baseConditionList) {
//            if (baseCondition.getType().equals("region")) {
//                sql.append(baseCondition.buildSql());
//            } else if (baseCondition.getType().equals("poi")) {
//                sql.append(baseCondition.buildSql("staypoint_15_100"));
//            }
//        }
//        filterConditionV2.setTableName("staypoint_15_100");
//        filterConditionV2.setBaseConditionList(baseConditionList);
//        stayPointService.searchFilterV2(filterConditionV2);
//        System.out.println(sql);
//        System.out.println(poiCondition.buildSql());
////        System.out.println(roadCondition.buildSql());
////        System.out.println(regionCondition.buildSql());






    }
}
