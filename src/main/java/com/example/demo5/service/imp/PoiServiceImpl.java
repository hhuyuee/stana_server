package com.example.demo5.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo5.entity.Poi;
import com.example.demo5.mapper.PoiMapper;
import com.example.demo5.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:41
 * poi service 的实现
 */
@Service
public class PoiServiceImpl extends ServiceImpl<PoiMapper, Poi>  implements PoiService {
    /**
     * 注入映射
     */
    @Autowired
    PoiMapper poiMapper;

    @Override
    public List<Poi> searchType(String type) {
        QueryWrapper<Poi> poiQueryWrapper = new QueryWrapper<>();
        poiQueryWrapper.eq("poi_type", type);
        return poiMapper.selectList(poiQueryWrapper);
    }

    @Override
    public List<Poi> searchPoiSpatial(String mbr) {
        return poiMapper.selectPoiSpatial(mbr);
    }
}
