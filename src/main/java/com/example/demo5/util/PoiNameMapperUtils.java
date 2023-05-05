package com.example.demo5.util;

/**
 * 用于poi的名字映射
 *
 * @author huyue87@jd.com
 * @date 2021/4/21 19:43
 * @since 2.0.0
 */
public class PoiNameMapperUtils {
    public static String getAoiName(String poiName) {
        if(poiName.equals("学校")) {
            return "'university'";
        } else if (poiName.equals("风景区")) {
            return "'theme_park'";
        }
        return null;
    }
}
