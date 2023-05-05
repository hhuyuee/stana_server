package com.example.demo5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo5.entity.Intersect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 查询交叉路口的数目
 *
 * @author huyue87@jd.com
 * @date 2020/11/30 17:23
 * @since 2.0.0
 */
@Mapper
@Component
public interface IntersectMapper extends BaseMapper<Intersect> {
    // 全量查询
    @Select(value = "select count(*)\n" +
            "from road_intersect, ${tableName}\n" +
            "where ST_DWithin(\n" +
            "\t${tableName}.point,\n" +
            "\troad_intersect.point,\n" +
            "\t100\n" +
            ")\n" +
            "group by(${tableName}.tid)")
    List<Integer> selectAll(@Param("tableName") String tableName);


    @Select(value ="select count(*)\n" +
            "from road_intersect\n" +
            "where St_Contains(\n" +
            "\tst_transform(st_geomfromtext(${mbrString},4326), 3857),\n" +
            "\troad_intersect.point\n" +
            ")\n")
    Integer selectSpatial(@Param("mbrString") String mbrString);

    /**
     * 以一个点圆心的空间范围查询  100m范围内的交叉路口数量
     * @param point
     * @return
     */
    @Select(value = "SELECT COALESCE(sum(num),0) FROM road_intersect\n" +
            "WHERE ST_DWithin\n" +
            "(road_intersect.point,\n" +
            " ST_Transform(ST_GeomFromText('${point}', 4326),3857),\n" +
            " 100\n" +
            " )")
    Integer selectSpatialTest(@Param("point") String point);

}
