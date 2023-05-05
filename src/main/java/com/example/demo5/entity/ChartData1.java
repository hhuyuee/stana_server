package com.example.demo5.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * 驻留点统计值
 *
 * @author huyue87@jd.com
 * @date 2020/12/14 16:05
 * @since 2.0.0
 */
@Data
@Accessors(chain = true)
public class ChartData1 {

    /**
     * 驻留点时长分布
     */
    List<Group> durationDistribute;
    /**
     * 停留时间分布（周）
     */
    List<Group> weekdayDistribute;

    /**
     * 停留时间分布 （日）
     */
    List<Group> timeDistribute;

    /**
     * 交叉路口数目
     */
    List<Group> intersectDistribute;

    /**
     * poi分布
     */
    List<Map<String, Object>> poiDistribute;

    /**
     * 1级道路分布
     */
    List<Group> road1Distribute;

    /**
     * 2级道路分布
     */
    List<Group> road2Distribute;

    /**
     * 3级道路分布
     */
    List<Group> road3Distribute;

}
