package com.example.demo5.service;

import com.example.demo5.entity.CandidatePlace;
import org.locationtech.jts.io.ParseException;

import java.util.concurrent.Future;

/**
 * 地点统计异步接口
 *
 * @author huyue87@jd.com
 * @date 2020/12/10 11:51
 * @since 2.0.0
 */
public interface PlaceAsyncService {
    /**
     * 执行异步任务
     * @return
     */
    Future<CandidatePlace> executeAsync(CandidatePlace candidatePlaceList) throws ParseException;
}
