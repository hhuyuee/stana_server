package com.example.demo5.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2021/1/6 11:28
 * @since 2.0.0
 */
@Data
@Accessors(chain = true)
public class PoiInfo {
    /**
     * poi名称
     */
    String poiName;

    /**
     * poi访问次数
     */
    Integer count;

    /**
     * Poi纬度
     */
    Double lat;
    /**
     * poi经度
     */
    Double lng;

}
