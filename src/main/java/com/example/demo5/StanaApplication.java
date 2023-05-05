package com.example.demo5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author huyue87
 * @date 2020-11-13 17:10 2020-11-13 17:30
 * spring boot 主启动类
 */
@SpringBootApplication
@MapperScan("com.example.demo5.mapper")
public class StanaApplication {
    /**
     * 主启动方法
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(StanaApplication.class, args);
    }
}
