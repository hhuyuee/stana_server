package com.example.demo5.service;

import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.StayPoint;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地点模式选择服务
 *
 * @author huyue87@jd.com
 * @date 2021/3/30 16:12
 * @since 2.0.0
 */
@Service
public interface ExistsPlaceService {
    /**
     * 为已有地点增加画像
     * @param stayPointList 驻留点集合
     * @param type 地点类型
     * @return
     */
    List<CandidatePlace> existPlace(List<StayPoint> stayPointList, String type);
}
