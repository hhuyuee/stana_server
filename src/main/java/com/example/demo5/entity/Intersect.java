package com.example.demo5.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/11/30 17:24
 * @since 2.0.0
 */
@Data
@TableName("road_intersect")
public class Intersect {
    /**
     * 交叉路口的点
     */
    String point;
    /**
     * 交叉路口的度数
     */
    int count;
}
