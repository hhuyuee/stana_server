package com.example.demo5.service.imp;

import com.example.demo5.entity.Group;
import com.example.demo5.entity.Poi;
import com.example.demo5.entity.StayPoint;
import com.example.demo5.service.PoiService;
import com.example.demo5.service.PointStatService;
import com.example.demo5.service.RoadDataService;
import com.example.demo5.service.StayPointService;
import com.example.demo5.util.DateUtils;
import com.example.demo5.util.StatisticUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-19 11:41
 * 实现驻留点统计服务
 */
@Service
public class PointStatServiceImpl implements PointStatService {
    @Autowired
    private PoiService poiService;
    @Autowired
    private StayPointService stayPointService;
    @Autowired
    private RoadDataService roadDataService;


    @Override
    public Date getStartTime(List<StayPoint> stayPointList) {
        return stayPointList.parallelStream()
                .map(StayPoint::getMidTime)
                .distinct()
                .min(DateUtils::compareDate)
                .get();
    }

    @Override
    public Date getEndTime(List<StayPoint> stayPointList) {
        return stayPointList.parallelStream()
                .map(StayPoint::getMidTime)
                .distinct()
                .max(DateUtils::compareDate)
                .get();
    }

    @Override
    public List<Group> durationStat(List<StayPoint> stayPointList) {
        List<Double> duration = stayPointList.parallelStream()
                .map(StayPoint::getDuration)
                .map(num -> num/60)
                .collect(Collectors.toList());
        // 统计 工具类
        return StatisticUtils.durationStat(duration);
    }

    @Override
    public List<Group> weekDayStat(List<StayPoint> stayPointList) {
        List<String> weekDay = stayPointList.parallelStream()
                .map(StayPoint::getWeek)
                .collect(Collectors.toList());
        // 统计 工具类
        return StatisticUtils.weekDayStat(weekDay);
    }

    @Override
    public List<Group> timeStat(List<StayPoint> stayPointList) {
        List<Date> date = stayPointList.parallelStream()
                .map(StayPoint::getMidTime)
                .collect(Collectors.toList());
        return StatisticUtils.timeSegStat(date);
    }



    @Override
    public List<Map<String, Object>> poiStat(List<Poi> poiList) {
        int sumCount = poiList.size();
        return poiList.parallelStream()
                .collect(Collectors.groupingBy(Poi::getPoiType,TreeMap::new, Collectors.toList()))
                .entrySet()
                .parallelStream()
                .map(
                        e->{
                            Map<String, Object> item = new TreeMap<>();
                            double prop = (double) e.getValue().size() / sumCount;
                            item.put("name", e.getKey());
                            item.put("value", prop);
                            return item;
                        }
                ).collect(Collectors.toList());

    }

    @Override
    public List<Group> roadStat(List<Double> roadLenList) {
        return StatisticUtils.roadStat(roadLenList);
    }

    @Override
    public List<Group> intersectStat(List<StayPoint> stayPointList) {
        List<Integer> intersectList = stayPointList.parallelStream()
                .map(StayPoint::getIntersectNum)
                .collect(Collectors.toList());
        return StatisticUtils.intersectStat(intersectList);
    }
}
