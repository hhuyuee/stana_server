package com.example.demo5;

import com.example.demo5.mapper.AoiMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2021/3/31 16:25
 * @since 2.0.0
 */
@SpringBootTest
public class AoiTest {
    @Autowired
    AoiMapper aoiMapper;

    @Test
    void test() {
//        List<Aoi> aoiList = aoiMapper.selectAll();
//        for(Aoi aoi: aoiList) {
//            String wkt = aoi.getGeom();
//            MultiPolygon multiPolygon = (MultiPolygon) GeoUtils.wkt2Geometry(wkt);
//            multiPolygon.
//        }
    }

}
