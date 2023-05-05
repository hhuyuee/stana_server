package com.example.demo5.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/11/26 19:59
 * @since 2.0.0
 * 用于交互的驻留点的信息
 */

@Data
@Accessors(chain = true)
public class StayPointVo {

    /**
     * 驻留点唯一 tid
     */
    private String tid;
    /**
     *  车辆产生的驻留点
     */
    private String oid;
    /**
     * 中间时间
     */
    private String midTime;
    /**
     * 驻留时长
     */
    private double duration;
    /**
     * 中心经度
     */
    private double lng;
    /**
     * 中心纬度
     */
    private double lat;
}
