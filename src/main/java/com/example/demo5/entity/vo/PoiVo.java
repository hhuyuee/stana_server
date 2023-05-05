package com.example.demo5.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * poiVo
 *
 * @author huyue87@jd.com
 * @date 2021/1/5 19:55
 * @since 2.0.0
 */

@Data
public class PoiVo {
    /**
     * poi类别
     */
    String poiType;
    /**
     * 同类poi
     */
    List<PoiInfo> poiInfos;

}
