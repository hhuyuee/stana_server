package com.example.demo5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo5.entity.Poi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:37
 * 数据库 poi表的映射
 */
@Mapper
@Component
public interface PoiMapper extends BaseMapper<Poi> {

    /**
     * 用于筛选驻留点附近的poi 用于统计
     * @param mbr
     * @return
     */
    @Select(value ="select distinct *\n" +
            "from poi\n" +
            "where St_Contains(\n" +
            "\tst_transform(st_geomfromtext(${mbr},4326), 3857),\n" +
            "\tpoi.geom\n" +
            ") and poi_type != 'unknown'\n")
    List<Poi> selectPoiSpatial(@Param("mbr") String mbr);

    @Select(value = "select poi.*\n" +
            "from poi,${tableName}\n" +
            "where ST_DWithin(\n" +
            "\t${tableName}.point,\n" +
            "\tpoi.geom,\n" +
            "\t100\n" +
            ") and poi_type != 'unknown'")
    List<Poi> selectAll(@Param("tableName") String tableName);

    // TODO 用于测试
    @Select(value = "select poi.*\n" +
            "from poi\n" +
            "where poi_type != 'unknown'")
    List<Poi> selectAllTest();

    // TODO 用于圆形测试
    @Select(value = "SELECT * FROM ${table}\n" +
            "WHERE ST_DWithin\n" +
            "(${table}.geom,\n" +
            " ST_Transform(ST_GeomFromText('${point}', 4326),3857),\n" +
            " 100\n" +
            " ) and ${table}.poi_type != 'unknown'")
    List<Poi> selectSpatialTest(@Param("point") String point, @Param("table") String table);

}
