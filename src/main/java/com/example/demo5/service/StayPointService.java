package com.example.demo5.service;

import com.example.demo5.entity.FilterConditionV2;
import com.example.demo5.entity.FilterConditions;
import com.example.demo5.entity.StayPoint;
import com.example.demo5.entity.StayPointInfo;
import com.example.demo5.entity.vo.StayPointVo;

import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-14 11:23 2020-11-14 11:38
 * 驻留点服务接口
 */
public interface StayPointService {

    /**
     * 查询全量驻留点 作采样
     * @return List
     */
    StayPointInfo searchAll(String tableName);

    /**
     * 筛选驻留点 并返回统计结果
     * @param filterConditions 筛选条件
     * @return 驻留点信息
     */
    StayPointInfo searchFilter(FilterConditions filterConditions);

    // TODO 第二个筛选版本
    StayPointInfo searchFilterV2(FilterConditionV2 filterConditionV2);
    /**
     * 返回驻留点不返回统计信息
     */
    // TODO 用于测试
    List<StayPointVo> searchFilterPoint(FilterConditions filterConditions);

    /**
     * poi查询
     * @param dis 距离
     * @param type poi类型
     * @return 驻留点集合
     */
    List<StayPoint> stayPointWithPoi(int dis, String type);

    /**
     * poi查询
     * @param dis 距离
     * @param level 等级
     * @return 驻留点集合
     */
    List<StayPoint> stayPointWithRoad(int dis, int level);

}
