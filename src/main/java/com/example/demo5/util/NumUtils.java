package com.example.demo5.util;

import java.util.*;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-19 14:19
 * 数据处理工具类
 */
public class NumUtils {
    /**
     * 返回数据统计结果
     * @param ini 个数
     * @return 统计结果
     */
    public static Map<String, String> computePro(Map<String, Long> ini, int sumCount) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, Long> entry : ini.entrySet()) {
            double tmp = entry.getValue()/(double) sumCount;
            String rates = String.format("%.2f", tmp * 100) + "%";
            result.put(entry.getKey(), rates);
        }
        return result;
    }

    /**
     * 返回数据统计结果
     * @param ini 个数
     * @return 统计结果
     */
    public static Map<String, Double> computePro1(Map<String, Long> ini, int sumCount) {
        Map<String, Double> result = new HashMap<>();
        for (Map.Entry<String, Long> entry : ini.entrySet()) {
            double tmp = entry.getValue()/(double) sumCount;
//            String rates = String.format("%.2f", tmp * 100) + "%";
            result.put(entry.getKey(), tmp);
        }
        return result;
    }
    /**
     * string 转list
     */
    public static List<String> string2List(String s) {
        String[] str = s.split("#");
        return Arrays.asList(str);
    }

    /**
     * 提取map中的value
     */
    public static List<Double> map2List(Map<String, Double> pairs) {
        List<Double> res = new ArrayList<>();
        for (Map.Entry<String, Double> entry : pairs.entrySet()) {
            res.add(entry.getValue());
        }
        return res;
    }

}
