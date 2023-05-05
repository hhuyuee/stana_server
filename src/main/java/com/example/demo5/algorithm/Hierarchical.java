package com.example.demo5.algorithm;


import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.StayPoint;
import com.example.demo5.util.GeoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-09 10:16 2020-11-10 14:51
 * 实现层次聚类的算法
 */
public class Hierarchical {
    /**
     *阈值
     */
    private double thresHold;
    /**
     * 输入数据
     */
    private List<StayPoint> data;
    /**
     * 输出簇
     */
    private List<CandidatePlace> result;

    /**
     * 构造函数
     * @param thresHold 阈值
     * @param data staypoint集合
     */
    public Hierarchical(double thresHold, List<StayPoint> data) {
        this.thresHold = thresHold;
        this.data = data;
    }
    /**
     * 初始化结果集
     */
    public List<CandidatePlace> initFinalResult() {
        // 把每个驻留点看成单独的一类
        List<CandidatePlace> startResult = new ArrayList<>();

        // 对于数据里的每一个驻留点
        for (StayPoint stayPoint : data) {
            List<StayPoint> init = new ArrayList<>();
            init.add(stayPoint);
            CandidatePlace tmp = new CandidatePlace(init);
            startResult.add(tmp);
        }
        return startResult;
    }

    /**
     * 生成最终的结果集
     */
    public List<CandidatePlace> hierarchicalResult() {
        List<CandidatePlace> result = initFinalResult();
        while(result.size() > 1) {
            // 计算类间的距离
            double[][] distanceArray = new double[result.size()][result.size()];
            // 初始化最小距离为前两个候选地点的距离
            double minDis = minDistance(result.get(0), result.get(1));
            int mergeIndex1 = 0;
            int mergeIndex2 = 1;
            for (int i = 0; i < result.size(); i++) {
                for (int j = i + 1; j < result.size(); j++) {
                    distanceArray[i][j] = minDistance(result.get(i), result.get(j));
//                    System.out.println(distanceArray[i][j]);
                    if(distanceArray[i][j] < minDis) {
                        minDis = distanceArray[i][j];
                        mergeIndex1 = i;
                        mergeIndex2 = j;
                    }
                }
            }
            if (minDis < thresHold) {
                merge(result, mergeIndex1, mergeIndex2);
            } else {
                break;
            }
        }

        return result;
    }

    /**
     * 合并两个候选地点
     */
    private List<CandidatePlace> merge(List<CandidatePlace> candidatePlaces, int mergeIndex1, int mergeIndex2) {
        if(mergeIndex1 != mergeIndex2) {
            CandidatePlace cP1 = candidatePlaces.get(mergeIndex1);
            CandidatePlace cP2 = candidatePlaces.get(mergeIndex2);
            cP1.getStayPointList().addAll(cP2.getStayPointList());
        }
        candidatePlaces.remove(mergeIndex2);
        return candidatePlaces;
    }

    /**
     *
     * @param cP1 类别1
     * @param cP2 类别2
     * @return 求出两类间的最小距离  (遍历候选地点中所有的staypoint)
     */
    private double minDistance(CandidatePlace cP1, CandidatePlace cP2) {
        double minDis = Double.POSITIVE_INFINITY;
        for (StayPoint sP1: cP1.getStayPointList()) {
            for(StayPoint sP2: cP2.getStayPointList()) {
                double tmp = GeoUtils.getDistanceOfMeter(sP1.getLat(), sP1.getLng(), sP2.getLat(), sP2.getLng());
                minDis = Math.min(minDis, tmp);
            }
        }
        return minDis;
    }
}
