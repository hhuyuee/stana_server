package com.example.demo5.service.imp;

import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.service.PlaceAsyncService;
import com.example.demo5.service.PlaceStatService;
import org.locationtech.jts.io.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * 地点统计任务
 *
 * @author huyue87@jd.com
 * @date 2020/12/10 11:54
 * @since 2.0.0
 */
@Service
public class PlaceAsyncServiceImpl implements PlaceAsyncService {

    @Autowired
    PlaceStatService placeStatService;


    private static final Logger logger = LoggerFactory.getLogger(PlaceAsyncServiceImpl.class);
    // 注入线程池 有返回值的多线程
    @Override
    @Async("defaultThreadPool")
    public Future<CandidatePlace> executeAsync(CandidatePlace candidatePlace) throws ParseException {
        logger.info("start stat");
        placeStatService.featureStillExtract(candidatePlace, 0.01);
        placeStatService.featureDynamicExtract(candidatePlace);
        logger.info("end stat");
        return new AsyncResult<>(candidatePlace);
    }
}
