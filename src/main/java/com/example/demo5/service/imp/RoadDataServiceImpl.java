package com.example.demo5.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo5.entity.RoadData;
import com.example.demo5.mapper.RoadDataMapper;
import com.example.demo5.service.RoadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


/**
 *
 * @author huyue87
 * @date 2020-11-14 17:09
 * RoadData Service 实现
 */
@Service
public class RoadDataServiceImpl extends ServiceImpl<RoadDataMapper, RoadData>  implements RoadDataService {
    /**
     * 注入映射
     */
    @Autowired
    RoadDataMapper roadDataMapper;


    @Override
    public List<RoadData> searchAll() {
        QueryWrapper<RoadData> queryWrapper = new QueryWrapper<>();
        return roadDataMapper.selectList(queryWrapper);
    }

    @Override
    public List<RoadData> searchSpatial(String mbrString) {
        return roadDataMapper.selectSpatial(mbrString);
    }

}
