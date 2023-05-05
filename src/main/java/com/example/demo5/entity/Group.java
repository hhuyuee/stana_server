package com.example.demo5.entity;

import lombok.Data;

/**
 * 用于统计分组
 *
 * @author huyue87@jd.com
 * @date 2020/12/9 17:19
 * @since 2.0.0
 */
@Data
public class Group {
    /**
     * 分组范围
     */
    String name;
    /**
     * 分组占比
     */
    Double value;
    // 用于解析
//    public Group() {
//
//    }
    /**
     * 构造函数
     */
    public Group(String name, Double value) {
        this.name = name;
        this.value = value;
    }
}
