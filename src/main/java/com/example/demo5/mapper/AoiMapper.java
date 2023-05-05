package com.example.demo5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo5.entity.Aoi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 获取Aoi数据
 *
 * @author huyue87@jd.com
 * @date 2021/3/30 16:48
 * @since 2.0.0
 */
@Mapper
@Component
public interface AoiMapper extends BaseMapper<Aoi> {
    // 筛选出对应类型的Aoi
    @Select(value = "select fclass, name, st_astext(geom) as geom from beijing_aoi_formal where fclass in (${type})")
    List<Aoi> selectAoiType(@Param("type") String type);

    @Select(value = "select name, st_astext(geom) as geom from beijing_aoi_formal")
    List<Aoi> selectAll();
}
