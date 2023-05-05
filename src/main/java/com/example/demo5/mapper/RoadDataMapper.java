package com.example.demo5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo5.entity.Group;
import com.example.demo5.entity.RoadData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author huyue87
 * @date 2020-11-14 14:22
 * road mapper
 */

@Mapper
@Component
public interface RoadDataMapper extends BaseMapper<RoadData> {
    /**
     * 路网的空间范围查询
     * @param mbr 路网所在的空间块
     * @return
     */
    @Select(value ="select distinct *\n" +
            "from road_net\n" +
            "where St_Contains(\n" +
            "\tst_transform(st_geomfromtext(${mbr},4326), 3857),\n" +
            "\troad_net.geom\n" +
            ")\n")
    List<RoadData> selectSpatial(@Param("mbr") String mbr);

    @Select(value = "select sum(road_len)\n" +
            "from road_net, ${tableName}\n" +
            "where ST_DWithin(\n" +
            "\t${tableName}.point,\n" +
            "\troad_net.geom,\n" +
            "\t50\n" +
            ") and road_net.level = #{level}\n" +
            "group by(${tableName}.tid)")
    List<Double> selectAll(@Param("tableName") String tableName, @Param("level") int level);

    // todo 用于测试圆形范围查询 500m 范围路网
    @Select(value = "SELECT ${road}.level, COALESCE(sum(level),0) FROM ${road}\n" +
            "WHERE ST_DWithin\n" +
            "(${road}.geom,\n" +
            "ST_Transform(ST_GeomFromText('${point}', 4326),3857),\n" +
            "500\n" +
            ") group by(${road}.level)")
    List<Group> selectSpatialTest(@Param("point") String point, @Param("road") String road);
}
