package com.example.demo5.service.imp;

import com.example.demo5.common.GeoPoint;
import com.example.demo5.entity.Aoi;
import com.example.demo5.entity.CandidatePlace;
import com.example.demo5.entity.StayPoint;
import com.example.demo5.mapper.AoiMapper;
import com.example.demo5.service.ExistsPlaceService;
import com.example.demo5.util.GeoUtils;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 已有地点统计
 *
 * @author huyue87@jd.com
 * @date 2021/3/30 16:26
 * @since 2.0.0
 */
@Service
public class ExistsPlaceServicesImpl implements ExistsPlaceService {

    /**
     * 获取已知地点
     */
    @Autowired
    AoiMapper aoiMapper;

    @Override
    public List<CandidatePlace> existPlace(List<StayPoint> stayPointList, String type) {
        // 获取地点列表
        List<Aoi> aoiList = aoiMapper.selectAoiType(type);
        aoiList.forEach(System.out::println);
        List<CandidatePlace> result = new ArrayList<>();
        // 获取地点中的驻留点列表（可以考虑优化）
        for (Aoi aoi : aoiList) {
            Geometry aoiPolygon = GeoUtils.wkt2Geometry(aoi.getGeom());
            List<StayPoint> stayPoints = new ArrayList<>();
            for (StayPoint stayPoint : stayPointList) {
                // wgs84 --> gcj02
//                Point spPointInit = (Point) GeoUtils.wkt2Geometry(stayPoint.getCentroid());
//                double[] coordArray = CoordTransformUtils.wgs84Togcj02(spPointInit.getX(), spPointInit.getY());
//                String pointWkt = "POINT(" + coordArray[0] + " " + coordArray[1] + ")";
                Point spPoint = (Point) GeoUtils.wkt2Geometry(stayPoint.getCentroid());
                assert aoiPolygon != null;
                if (aoiPolygon.contains(spPoint)) {
                    stayPoints.add(stayPoint);
                }
            }
            if (stayPoints.size() != 0) {
                System.out.println("该地点包含的驻留点个数：" + stayPoints.size());
                aoi.setStayPointList(stayPoints);
                result.add(aoi2CandidatePlace(aoi));
            }
//             打印北京欢乐谷访问的驻留点
            if (aoi.getName().equals("北京大学")) {
                stayPoints.forEach(s -> {
                    Date d = s.getMidTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                    System.out.println(sdf.format(d));
                });
            }
        }
        return result;
    }

    private CandidatePlace aoi2CandidatePlace(Aoi aoi) {
        CandidatePlace candidatePlace = new CandidatePlace();
        // 求aoi 质心
        MultiPolygon aoiPolygon = (MultiPolygon) GeoUtils.wkt2Geometry(aoi.getGeom());
        //
        assert aoiPolygon != null;
        String polygon = aoiPolygon.convexHull().toString();
        List<Coordinate> coordinates = Arrays.asList(aoiPolygon.getCoordinates());
        GeoPoint centerPoint = GeoUtils.getCenterCoordinates(coordinates);
        candidatePlace.setStayPointList(aoi.getStayPointList())
                .setPolygon(polygon)
                .setLng(centerPoint.getLng())
                .setLat(centerPoint.getLat())
                .setName(aoi.getName());
        return candidatePlace;
    }
}
