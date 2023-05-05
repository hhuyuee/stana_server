package com.example.demo5;

import com.alibaba.fastjson.JSON;
import com.example.demo5.entity.condition.StayPlaceCondition;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2021/4/22 16:26
 * @since 2.0.0
 */
public class JsonTest {
    public static void main(String[] args) {
        String jsonStr = "{\"type\":\"oftenstay\",\"stayPlace\":\"学校\"}";
        StayPlaceCondition baseCondition = JSON.parseObject(jsonStr, StayPlaceCondition.class);

        System.out.println(baseCondition);
    }
}
