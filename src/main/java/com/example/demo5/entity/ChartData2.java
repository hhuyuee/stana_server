package com.example.demo5.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * 单个地点统计值
 *
 * @author huyue87@jd.com
 * @date 2020/12/14 16:05
 * @since 2.0.0
 */

@Data
@Accessors(chain = true)
public class ChartData2 {
    /**
     * 地点停留时长分布
     */
    List<Group> durationDistribute;
    /**
     * 停留时间分布 （日）
     */
    List<Group> timeDistribute;

    /**
     * 停留时间分布（周）
     */
    List<Group> weekDistribute;
    /**
     * 访问频率分布
     */
    List<Group> freqDistribute;
    /**
     * 年份分布
     */
    List<Map<String, Object>> yearDistribute;
    /**
     * 月份分布
     */
    List<Map<String, Object>> monthDistribute;
    /**
     * poi分布
     */
    List<Map<String, Object>> poiDistribute;


}
