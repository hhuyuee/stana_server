package com.example.demo5;

import com.example.demo5.mapper.IntersectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/12/7 11:39
 * @since 2.0.0
 */
@SpringBootTest
public class IntersectTest {
    @Autowired
    IntersectMapper intersectMapper;
    @Test
    void test() {
//        Integer sum = intersectMapper.selectSpatial("POLYGON((115.34368 39.96596,115.34368 39.96672,116.34419 39.96672,116.34419 39.96596,115.34368 39.96596))");
//        System.out.println(sum);
    }
}
