package com.example.demo5;

import com.example.demo5.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CachePut;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisUtils redisUtils;



    @CachePut(value = "index", key = "'123'")
    public String testDemo() {
        return "123";
    }


    @Test
    public void test() {
        System.out.println(testDemo());
        redisUtils.set("123","TEST");
        System.out.println(redisUtils.get("123"));

    }
}
