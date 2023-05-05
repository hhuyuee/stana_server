package com.example.demo5.algorithm;

import com.example.demo5.entity.StayPoint;
import com.example.demo5.util.GeoUtils;
import org.locationtech.jts.io.ParseException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author huyue87
 * @date 2020-11-09 10:16 2020-11-10 20:52
 * dbscan 密度聚类
 */
public class DBScanCluster implements Serializable {
    /**
     * 所有聚类点对象
     */
    List<StayPoint> srcPoints;

    /**
     * 核要素密度可达的子核要素栈
     */
    private Stack<StayPoint> subCoreStack;
    /**
     * 核要素最小样本个数
     */
    private int minPoints;

    /**
     * 要素搜索半径
     */
    protected double radius;

    /**
     * 核要素集合
     */
    private List<StayPoint> cores;

    /**
     * 当前类别号
     */
    private int clusterID = 1;


    /**
     * 构造函数
     *
     * @param srcPoints  原始点数据
     * @param minPoints  核要素最小样本点个数
     * @param radius     聚类搜索半径
     */
    public DBScanCluster(List<StayPoint> srcPoints, int minPoints, double radius) {
        this.srcPoints = srcPoints;
        this.minPoints = minPoints;
        this.radius = radius;
        this.cores = new ArrayList<>();
        this.subCoreStack = new Stack<>();
    }

    /**
     * 原始点数据
     * @param srcPoints
     */
    public DBScanCluster(List<StayPoint> srcPoints) {
        this.srcPoints = srcPoints;
    }

    /**
     * 开始执行聚类
     */
    public void doCluster() throws ParseException {
        if (srcPoints == null) {
            throw new RuntimeException("srcPoints为空");
        }
        //遍历srcPoints找到cores，并标记密度直达点
        System.out.println("开始找核心要素");
        findCores();
        //遍历cores，循环每个密度直达点，标记所有密度相连点的类别
        for (StayPoint corePt : cores) {
            if (corePt.isVisited()) {
                continue;
            }
            corePt.setClusterID(this.clusterID++);
            densityConnect(corePt);
        }
    }

    /**
     * 找核要素
     */
    private void findCores() throws ParseException {
        if (srcPoints == null) {
            return;
        }
        for (StayPoint curPt : srcPoints) {
            List<StayPoint> adjacentPoints = getAdjacentPoints(curPt);
            //包括自己
            if (adjacentPoints != null && adjacentPoints.size() >= minPoints) {
                curPt.setCore(true);
                curPt.setAdjacentPoints(adjacentPoints);
                this.cores.add(curPt);
            }
        }
    }

    /**
     * 找核要素密度相连要素并指定类别
     *
     * @param corePt 核要素点
     */
    private void densityConnect(StayPoint corePt) {
        subCoreStack.push(corePt);
        while (!subCoreStack.isEmpty()) {
            StayPoint subCore = subCoreStack.pop();
            subCore.setVisited(true);
            List<StayPoint> adjacentPoints = subCore.getAdjacentPoints();
            if (adjacentPoints == null || adjacentPoints.size() == 0) {
                continue;
            }
            for (StayPoint adjPt : adjacentPoints) {
                if (adjPt.isVisited()) {
                    continue;
                }
                adjPt.setVisited(true);
                adjPt.setClusterID(corePt.getClusterID());
                if (adjPt.isCore()) {
                    subCoreStack.push(adjPt);
                }
            }
        }
    }

    /**
     * 寻找目标点相邻的密度直达点
     *
     * @param centerPt 目标点
     * @return 密度直达点
     */
    protected List<StayPoint> getAdjacentPoints(StayPoint centerPt) throws ParseException {
        List<StayPoint> adjacentPoints = new ArrayList<>();
        for (StayPoint refPt : srcPoints) {
            double dis;
            dis = calDistance(centerPt, refPt);
            if (dis < this.radius) {
                adjacentPoints.add(refPt);
            }
        }
        return adjacentPoints;
    }

    /**
     * 计算两个sP2的地理位置
     * @param sP1
     * @param sP2
     * @return
     */
    private double calDistance(StayPoint sP1, StayPoint sP2) {
        return GeoUtils.getDistanceOfMeter(sP1.getLat(), sP1.getLng(), sP2.getLat(), sP2.getLng());
    }
}

