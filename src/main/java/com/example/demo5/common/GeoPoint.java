package com.example.demo5.common;

import lombok.Data;

import java.text.NumberFormat;

/**
 *
 * @author huyue87
 * @date 2020-11-14 16:52
 * 表示地理的坐标点
 */
@Data
public class GeoPoint {
    /**
     * 经度
     */
    private double lng;
    /**
     * 纬度
     */
    private double lat;

    /**
     * 构造函数
     * @param lng 经度
     * @param lat 纬度
     */
    public GeoPoint(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }


    @Override
    public String toString() {
        return "POINT(" + double2Str(this.lng) + " " + double2Str(this.lat) + ")";
    }

    public static String double2Str(Double d) {
        if (d == null) {
            return "";
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return (nf.format(d));
    }

}
