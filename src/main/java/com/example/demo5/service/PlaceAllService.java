package com.example.demo5.service;
import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.Group;

import java.util.List;
import java.util.Map;
/**
 * 地点信息的统计
 *  * 地点集合信息的统计
 *  * 访问次数分布
 *  * 平均访问频次分布
 *  * 平均访问时长分布
 *  * 访问对象个数分布
 *  * 交叉路口个数
 *  * 周围poi分布
 *  * 一、二、三级路网长度分布
 *  * 访问时间分布
 * @author huyue87@jd.com
 * @date 2020/12/7 18:32
 * @since 2.0.0
 */
public interface PlaceAllService {
    /**
     * 候选地点的访问次数分布
     */
    List<Group> visitSumStat(List<CandidatePlace> candidatePlaces);
    /**
     * 平均访问频次
     */
    List<Group> freqMeanStat(List<CandidatePlace> candidatePlaces);
    /**
     * 平均访问时长分布
     */
    List<Group> durationAvgStat(List<CandidatePlace> candidatePlaces);
    /**
     * 访问对象分布
     */
    List<Group> visitObjStat(List<CandidatePlace> candidatePlaces);
    /**
     * 交叉路口分布
     */
    List<Group> intersectStat(List<CandidatePlace> candidatePlaces);
    /**
     * 周围poi分布
     */
    List<Map<String, Object>> poiTypeStat(List<CandidatePlace> candidatePlaces);
    /**
     * 访问时间分布
     */
    List<Group> timeSegStat(List<CandidatePlace> candidatePlaces);
    /**
     * 一级路网
     */
    List<Group> road1lenStat(List<CandidatePlace> candidatePlaces);
    /**
     * 二级路网
     */
    List<Group> road2lenStat(List<CandidatePlace> candidatePlaces);
    /**
     * 三级路网
     */
    List<Group> road3lenStat(List<CandidatePlace> candidatePlaces);
    /**
     * poi 具体类别
     */
    List<Map<String, Object>> poiTopNameStat(List<CandidatePlace> candidatePlaces);

}
