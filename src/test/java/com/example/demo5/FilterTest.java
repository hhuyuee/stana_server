package com.example.demo5;

import com.example.demo5.mapper.StayPointMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/11/26 20:25
 * @since 2.0.0
 */
@SpringBootTest
public class FilterTest {
    @Autowired
    StayPointMapper stayPointMapper;

    @Test
    void test() {
        long startTime1 = System.currentTimeMillis();    //获取开始时间
//        List<StayPoint> stayPointList1 = stayPointMapper.selectDuration();
        long endTime1 = System.currentTimeMillis();    //获取结束时间
//        System.out.println(stayPointList1.size());
        System.out.println("数据库筛选运行时间1：" + (endTime1 - startTime1) + "ms");    //输出程序运行时间
//        long startTime2 = System.currentTimeMillis();    //获取开始时间
//        List<StayPoint> stayPointList2 = stayPointMapper.selectOid();
//        long endTime2 = System.currentTimeMillis();    //获取结束时间
//        System.out.println(stayPointList2.size());
//        System.out.println("数据库运行时间2：" + (endTime2 - startTime2) + "ms");    //输出程序运行时间
//        stayPointList1.addAll(stayPointList2);
//        long startTime3 = System.currentTimeMillis();    //获取开始时间
//        List<StayPoint> stayPointList3 = stayPointList1.parallelStream().filter(stayPointList2::contains).collect(Collectors.toList());
//        long endTime3 = System.currentTimeMillis();    //获取结束时间
//        System.out.println(stayPointList3.size());
//        System.out.println("内存集合运算程序运行时间3：" + (endTime3 - startTime1) + "ms");    //输出程序运行时间

//        long startTime4 = System.currentTimeMillis();    //获取开始时间
//        List<StayPoint> stayPointList4 = stayPointMapper.selectTest();
//        long endTime4 = System.currentTimeMillis();    //获取结束时间
//        System.out.println(stayPointList4.size());
//        System.out.println("数据库运算程序运行时间4：" + (endTime4 - startTime4) + "ms");    //输出程序运行时间


//        long startTime5 = System.currentTimeMillis();    //获取开始时间
//        List<StayPoint> stayPointList5 = stayPointMapper.selectAll().parallelStream().filter(item->item.getDuration()>9000).collect(Collectors.toList());
//        System.out.println(stayPointList5.size());
//        long endTime5 = System.currentTimeMillis();    //获取结束时间
//        System.out.println("内存筛选运算程序运行时间4：" + (endTime5 - startTime5) + "ms");    //输出程序运行时间



    }

}
