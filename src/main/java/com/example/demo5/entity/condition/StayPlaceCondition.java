package com.example.demo5.entity.condition;

import lombok.Data;

/**
 * 常驻地条件
 *
 * @author huyue87@jd.com
 * @date 2021/4/22 14:39
 * @since 2.0.0
 */
@Data
public class StayPlaceCondition extends BaseCondition {
    // 常驻时间段
//    int stayTimeSeg;
    // 常驻地点类型
    String stayPlace;

    public StayPlaceCondition() {
        super();
    }

    public StayPlaceCondition(String type, String stayPlace) {
        super(type);
//        this.stayTimeSeg = stayTimeSeg;
        this.stayPlace = stayPlace;
    }

    @Override
    public String buildSql() {
        return "\nand oid in " + "(select oid from geolife_often_stay where place_type = '" + stayPlace
        + "\')";
    }
}
