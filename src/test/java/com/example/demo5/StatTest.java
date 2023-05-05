package com.example.demo5;

import com.example.demo5.service.PointStatService;
import com.example.demo5.service.StayPointService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatTest {
    @Autowired
    StayPointService stayPointService;
    @Autowired
    PointStatService pointStatService;

    @Test
    void test() {
//        List<StayPoint> temp =stayPointService.stayPointWithPoi(50, "加油站");
//        System.out.println(temp.size());
//        pointStatService.durationStat(temp);
//        pointStatService.timeStat(temp);
//        pointStatService.weekDayStat(temp);
//        pointStatService.poiStat(temp);
//        pointStatService.roadStat(temp, 3);
    }
}
