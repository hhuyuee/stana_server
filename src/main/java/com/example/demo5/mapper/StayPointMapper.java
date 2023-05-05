package com.example.demo5.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo5.entity.*;
import com.example.demo5.entity.condition.BaseCondition;
import com.example.demo5.util.NumUtils;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 * @author huyue87
 * @date 2020-11-14 11:23 2020-11-14 11:32
 * 用于数据库的映射
 */
@Mapper
@Component
public interface StayPointMapper extends BaseMapper<StayPoint> {

    // 按照表名查询全量驻留点
    @Select(value = "select s.tid, s.oid, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat,s.duration, s.region\n from ${tableName} as s")
    List<StayPoint> selectAll(@Param("tableName") String tableName);

    // 按照筛选条件筛选驻留点
    @SelectProvider(type = StayPointBuilder.class, method = "queryAttribute")
    @Results({
            @Result(column = "tid", property = "tid"),
            @Result(column = "oid", property = "oid"),
            @Result(column = "mid_time", property = "midTime"),
            @Result(column = "centroid", property = "centroid"),
            @Result(column = "lng", property = "lng"),
            @Result(column = "lat", property = "lat"),
            @Result(column = "duration", property = "duration"),
            @Result(column = "region", property = "region"),
            @Result(column = "point", property = "point")
    })
    List<StayPoint> selectAttribute(FilterConditions filterConditions);

    // TODO 按照筛选条件筛选驻留点 V2 测试 合理就保留
    @SelectProvider(type = StayPointBuilder.class, method = "stayPointQuery")
    @Results({
            @Result(column = "tid", property = "tid"),
            @Result(column = "oid", property = "oid"),
            @Result(column = "mid_time", property = "midTime"),
            @Result(column = "centroid", property = "centroid"),
            @Result(column = "lng", property = "lng"),
            @Result(column = "lat", property = "lat"),
            @Result(column = "duration", property = "duration"),
            @Result(column = "region", property = "region"),
            @Result(column = "point", property = "point"),
            @Result(column = "week", property = "week")
    })
    List<StayPoint> selectAttributeV2(FilterConditionV2 filterConditionV2);


    @SelectProvider(type = StayPointBuilder.class, method = "poiQuery")
    @Results({
            @Result(column = "gid", property = "gid"),
            @Result(column = "poi_id", property = "poiId"),
            @Result(column = "lng", property = "lng"),
            @Result(column = "lat", property = "lat"),
            @Result(column = "poi_type_id", property = "poiTypeId"),
            @Result(column = "poi_name", property = "poiName"),
            @Result(column = "poi_type", property = "poiType"),
    })
    List<Poi> selectPoi(FilterConditionV2 filterConditionV2);

    @SelectProvider(type = StayPointBuilder.class, method = "roadQuery")
    List<Double> selectRoad(FilterConditionV2 filterConditionV2, int level);

    // TODO 路网测试优化
    @SelectProvider(type = StayPointBuilder.class, method = "roadQueryV2")
    @Results({
            @Result(column = "level", property = "name"),
            @Result(column = "sum", property = "value"),
    })
    List<Group> selectRoadV2(FilterConditionV2 filterConditionV2);

    @SelectProvider(type = StayPointBuilder.class, method = "poiNameQuery")
    @Results({
            @Result(column = "poi_type", property = "poiType"),
            @Result(column = "poi_name", property = "poiName"),
            @Result(column = "cnt", property = "count"),
            @Result(column = "lng", property = "lng"),
            @Result(column = "lat", property = "lat")
    })
    List<Poi> poiNameQuery(FilterConditionV2 filterConditionV2);


    /**
     * 路网+驻留点
     * @param distance 距离
     * @param level 路网等级
     * @return map(key-> value)
     */
    @Select(value = "select *\n" +
            "        from staypoint_15_50 s ,road_net r\n" +
            "        where st_dwithin(\n" +
            "            s.point,\n" +
            "            r.geom,\n" +
            "            #{dis, jdbcType = INTEGER}\n" +
            "        ) and r.level = #{level, jdbcType = INTEGER}")
    List<Map<String, Object>> stayPointWithRoad(@Param("dis") int distance, @Param("level") int level);

    /**
     * poi + 驻留点
     * @param distance 距离
     * @param type 驻留点种类
     * @return map map(key-> value)
     */
    @Select(value = "select s.tid, s.oid, s.start_time, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat, st_astext(s.mbr) as mbr, s.duration, st_astext(s.point) as point, s.area\n" +
            "        from staypoint_15_50 s ,poi p\n" +
            "        where st_dwithin(\n" +
            "            s.point,\n" +
            "            p.geom,\n" +
            "            #{dis, jdbcType = INTEGER}\n" +
            "        ) and p.poi_type = #{type, jdbcType = VARCHAR}")
    List<Map<String, Object>> stayPointWithPoi(@Param("dis") int distance, @Param("type") String type);




    /***
     * 动态构造查询语句
     *
     * @author huyue87@jd.com
     * @since 2.0.0
     * @date 2020/11/28 19:45
     */
    class StayPointBuilder {

        /**
         * description
         *
         * @param filterConditions 单表+多表查询条件
         * @return java.lang.String
         * @throws
         * @since 2.0.0
         */
        public String queryAttribute(FilterConditions filterConditions) {
            String roadNet = "road_net_beijing";
            String roadNetGeom = "road_net_beijing.geom";
            String roadNetLevel = "road_net_beijing.level";
            String poiTable = "poi_beijing";
            String poiTableGeom = "poi_beijing.geom";
            String poiTableType = "poi_beijing.poi_type";
            if(filterConditions.getTableName().startsWith("nantong")) {
                roadNet = "road_net_nantong";
                roadNetGeom = "road_net_nantong.geom";
                roadNetLevel = "road_net_nantong.level";
                poiTable = "poi_nantong";
                poiTableGeom = "poi_nantong.geom";
                poiTableType = "poi_nantong.poi_type";
            }
            StringBuilder sql = new StringBuilder();
            sql.append("select s.tid, s.oid, s.mid_time, st_astext(s.centroid), s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num\n from ");
            if (filterConditions.getTableName() == null) {
                //  TODO 表名是肯定不为空的！
                System.out.println("请输入表名！");
            }
            sql.append(filterConditions.getTableName()).append(" as s");
            sql.append("\nwhere 1 = 1 ");

            // 时间范围查询
            if (filterConditions.getSTime() != null) {
                sql.append("\nand s.mid_time > '").append(filterConditions.getSTime()).append("'");
            }
            if (filterConditions.getETime() != null) {
                sql.append("\nand s.mid_time < '").append(filterConditions.getETime()).append("'");
            }

             // 空间范围查询
            if (filterConditions.getMbrStringStr() != null) {
                List<String> mbrList = NumUtils.string2List(filterConditions.getMbrStringStr());
                for (int i = 0; i < mbrList.size(); i++) {
                    if (i == 0) {
                        sql.append("\nand st_contains(st_transform(st_geomfromtext('").append(mbrList.get(i)).append("',4326), 3857), s.point)");
                    } else {
                        sql.append("\nor st_contains(st_transform(st_geomfromtext('").append(mbrList.get(i)).append("',4326), 3857), s.point)");
                    }
                }
            }

            // 行政区划查询
            if (filterConditions.getRegionStr() != null) {
                sql.append("and s.region in (");
                List<String> regionList = NumUtils.string2List(filterConditions.getRegionStr());
                StringBuilder tempRegion = new StringBuilder();
                for (int i = 0; i < regionList.size(); i++) {
                    if(i != regionList.size() - 1) {
                        tempRegion.append("'").append(regionList.get(i)).append("'").append(",");
                    } else {
                        tempRegion.append("'").append(regionList.get(i)).append("'").append(")");
                    }
                }
                sql.append(tempRegion);
            }

            // duration字段查询 -> 涉及到正向筛选和反向筛选
            if (filterConditions.getDurationSymbol() != null && filterConditions.getDuration() != null) {
                sql.append("\nand s.duration ").append(filterConditions.getDurationSymbol()).append(filterConditions.getDuration() * 60);
            }
            // 交叉路口筛选
            if (filterConditions.getIntersectionNum() != null && filterConditions.getIntersectSymbol() != null) {
                sql.append("\n and s.intersect_num ").append(filterConditions.getIntersectSymbol()).append(filterConditions.getIntersectionNum());
            }
            // poi 条件
            if(filterConditions.getDisPoi() != null && filterConditions.getPType() != null && filterConditions.getPoiFilter() != null) {
                sql.append("\nintersect");
                // 范围外查询
                if (filterConditions.getPoiFilter() == 0) {
                     sql.append("\nselect s.tid, s.oid, s.mid_time, st_astext(s.centroid), s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num\nfrom ")
                             .append(filterConditions.getTableName())
                             .append(" as s")
                             .append("\nexcept");
                }
                sql.append("\nselect s.tid, s.oid, s.mid_time, st_astext(s.centroid), s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num\nfrom ")
                        .append(filterConditions.getTableName())
                        .append(" as s,")
                        .append(poiTable)
                        .append("\nwhere ST_Dwithin(\n s.point,\n")
                        .append(poiTableGeom)
                        .append(",\n")
                        .append(filterConditions.getDisPoi())
                        .append(")\nand ")
                        .append(poiTableType)
                        .append(" in (");

                StringBuilder tempPoi = new StringBuilder();
                List<String> pTypeList = NumUtils.string2List(filterConditions.getPType());
                for (int i = 0; i < pTypeList.size(); i++) {
                    if(i != pTypeList.size() - 1) {
                        tempPoi.append("'").append(pTypeList.get(i)).append("'").append(",");
                    } else {
                        tempPoi.append("'").append(pTypeList.get(i)).append("'").append(")");
                    }
                }
                sql.append(tempPoi);
            }
            // 路网条件
            if(filterConditions.getDisRoad() != null && filterConditions.getRType() != null && filterConditions.getRoadFilter() != null) {
                sql.append("\nintersect\nselect s.tid, s.oid, s.mid_time, st_astext(s.centroid), s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num\nfrom ")
                        .append(filterConditions.getTableName())
                        .append(" as s, ")
                        .append(roadNet);
                if(filterConditions.getRoadFilter() == 1) {
                    sql.append("\nwhere ST_Dwithin(\n s.point,\n")
                        .append(roadNetGeom)
                        .append(",\n");
                } else {
                    sql.append("\nwhere not ST_Dwithin(\n s.point,\n")
                            .append(roadNetGeom)
                            .append(",\n");;
                }
                sql.append(filterConditions.getDisRoad())
                        .append(")\nand ")
                        .append(roadNetLevel)
                        .append(" in (");
                StringBuilder tempPoi = new StringBuilder();
                List<String> rTypeList = NumUtils.string2List(filterConditions.getRType());
                for (int i = 0; i < rTypeList.size(); i++) {
                    if(i != rTypeList.size() - 1) {
                        tempPoi.append("'").append(rTypeList.get(i)).append("'").append(",");
                    } else {
                        tempPoi.append("'").append(rTypeList.get(i)).append("'").append(")");
                    }
                }
                sql.append(tempPoi);
            }
            // 星期查询
            if(filterConditions.getWeekDaysStr() != null) {
                List<String> weekLis = NumUtils.string2List(filterConditions.getWeekDaysStr());
                sql.append("\nintersect\nselect s.tid, s.oid, s.mid_time, st_astext(s.centroid), s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num\nfrom ")
                        .append(filterConditions.getTableName())
                        .append(" as s\nwhere week in (");
                StringBuilder tempSeq = new StringBuilder();
                for (int i = 0; i < weekLis.size(); i++) {
                    if(i != weekLis.size() - 1) {
                        tempSeq.append("'").append(weekLis.get(i)).append("',");
                    } else {
                        tempSeq.append("'").append(weekLis.get(i)).append("')");
                    }
                }
                sql.append(tempSeq);
            }
            // 时间段查询
            if (filterConditions.getTimeSequencesStr() != null) {
                List<String> sequenceLis = NumUtils.string2List(filterConditions.getTimeSequencesStr());
                sql.append("\nintersect\nselect s.tid, s.oid, s.mid_time, st_astext(s.centroid), s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num\nfrom ")
                        .append(filterConditions.getTableName())
                        .append(" as s\nwhere substring(mid_time,12,2) in (");
                StringBuilder tempSeq = new StringBuilder();
                for (int i = 0; i < sequenceLis.size(); i++) {
                    if(i != sequenceLis.size() - 1) {
                        tempSeq.append("'").append(sequenceLis.get(i)).append("',");
                    } else {
                        tempSeq.append("'").append(sequenceLis.get(i)).append("')");
                    }
                }
                sql.append(tempSeq);

            }

            System.out.println(sql.toString());
            return sql.toString();
        }

        /**
         * 驻留点筛选V2
         */
        public String stayPointQuery(FilterConditionV2 filterConditionV2) {
            StringBuilder sql = new StringBuilder();
            String poiTable = "poi_beijing";
            String roadTable = "road_net_beijing";
            if(filterConditionV2.getTableName().startsWith("nantong")) {
                poiTable = "poi_nantong";
                roadTable = "road_net_nantong";
            }
            // 获取条件数目
            int size = filterConditionV2.getConditionList().size();
            if (size == 0) {
                sql.append("select s.tid, s.oid, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num, s.week\n from ").append(filterConditionV2.getTableName()).append(" as s");
                System.out.println(sql.toString());
                return sql.toString();
            }
            int count = 0;
            // 建立子表视图的个数
            int tableSequence = 0;
            // 是否存在普通屏属性
            boolean flag = false;
            // 考虑只有一个属性
            if (size == 1 && filterConditionV2.getConditionList().get(0).getType().equals("poi")) {
                BaseCondition baseCondition = filterConditionV2.getConditionList().get(0);
                sql.append(baseCondition.buildSql(filterConditionV2.getTableName(), poiTable));
                System.out.println(sql.toString());
                return sql.toString();
            } else if (size == 1 && filterConditionV2.getConditionList().get(0).getType().equals("road")) {
                BaseCondition baseCondition = filterConditionV2.getConditionList().get(0);
                sql.append(baseCondition.buildSql(filterConditionV2.getTableName(), roadTable));
                System.out.println(sql.toString());
                return sql.toString();
            }
            // 获取普通属性数目 (判断是否有普通属性)
            for (BaseCondition baseCondition: filterConditionV2.getConditionList()) {
                String tmp = baseCondition.getType();
                if (!tmp.equals("poi") && !tmp.equals("road")) {
                    flag = true;
                }
            }
            // 存在普通属性
            if (flag) {
                // tablename -> temp1
                sql.append("with temp1 as\n (select s.tid, s.oid, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num, s.week\n from ");
                sql.append(filterConditionV2.getTableName()).append(" as s");
                sql.append("\nwhere 1 = 1 ");
                // 注意排序
                for (BaseCondition baseCondition : filterConditionV2.getConditionList()) {
                    String tmp = baseCondition.getType();
                    if (!tmp.equals("poi") && !tmp.equals("road")) {
                        sql.append(baseCondition.buildSql());
                        count ++;
                    }
                }
                sql.append(")\n");
                if (count != size) {
                    sql.append(",");
                }
                tableSequence ++;
                for (BaseCondition baseCondition : filterConditionV2.getConditionList()) {
                    String tmp = baseCondition.getType();
                    if (tmp.equals("poi")) {
                        sql.append(baseCondition.buildSql(tableSequence, poiTable));
                        tableSequence ++;
                    } else if (tmp.equals("road")) {
                        sql.append(baseCondition.buildSql(tableSequence, roadTable));
                        tableSequence ++;
                    }
                }
            } else {
                // 不存在普通属性
                sql.append("with temp1 as (");
                BaseCondition first = filterConditionV2.getConditionList().get(0);
                sql.append(first.buildSql(filterConditionV2.getTableName())).append("),");
                tableSequence ++;
                for (int i = 1; i < filterConditionV2.getConditionList().size(); i++) {
                    BaseCondition tmp = filterConditionV2.getConditionList().get(i);
                    sql.append(tmp.buildSql(tableSequence));
                    tableSequence ++;
                }
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append("\n select * from temp" + tableSequence);
//            System.out.println(sql.toString());
            return sql.toString();

        }

        // poi、路网统计v2
        /**
         * poi统计
         */
        public String poiQuery(FilterConditionV2 filterConditionV2) {
            StringBuilder sql = new StringBuilder();
            String poiTable = "poi_beijing";
            String poiTableGeom = "poi_beijing.geom";
            String poiTableType = "poi_beijing.poi_type";
            if (filterConditionV2.getTableName().startsWith("nantong")) {
                poiTable = "poi_nantong";
                poiTableGeom = "poi_nantong.geom";
                poiTableType = "poi_nantong.poi_type";
            }
            sql.append("with temp as (").append(stayPointQuery(filterConditionV2)).append(")\n");
            sql.append("\nselect ")
                .append(poiTable)
                .append(".*\n from ")
                .append(poiTable)
                .append(", temp\n where ST_Dwithin(\ntemp.point,\n")
                .append(poiTableGeom)
                .append(",\n50\n) and ")
                .append(poiTableType)
                .append( "!= 'unknown'");
            return sql.toString();
        }
        /**
         * 路网统计
         */
        public String roadQuery(FilterConditionV2 filterConditionV2, int level) {
            StringBuilder sql = new StringBuilder();
            String roadNet = "road_net_beijing";
            String roadNetGeom = "road_net_beijing.geom";
            if (filterConditionV2.getTableName().startsWith("nantong")) {
                roadNet = "road_net_nantong";
                roadNetGeom = "road_net_nantong.geom";
            }
            sql.append("with temp as (").append(stayPointQuery(filterConditionV2)).append(")\n");
            sql.append("\nselect sum(road_len)\n from ")
                    .append(roadNet)
                    .append(", temp\nwhere ST_Dwithin(\ntemp.point,\n")
                    .append(roadNetGeom)
                    .append(",\n50\n) and level = ")
                    .append(level)
                    .append(" group by temp.tid");
            System.out.println(sql.toString());
            return sql.toString();
        }
        /**
         * 路网统计优化
         */
        public String roadQueryV2(FilterConditionV2 filterConditionV2) {
            StringBuilder sql = new StringBuilder();
            String roadNet = "road_net_beijing";
            String roadNetGeom = "road_net_beijing.geom";
            if (filterConditionV2.getTableName().startsWith("nantong")) {
                roadNet = "road_net_nantong";
                roadNetGeom = "road_net_nantong.geom";
            }
            sql.append("with temp as (").append(stayPointQuery(filterConditionV2)).append(")\n");

            sql.append("\nselect level, sum(road_len)\n from ")
                    .append(roadNet)
                    .append(", temp\nwhere ST_Dwithin(\ntemp.point,\n")
                    .append(roadNetGeom)
                    .append(",\n200.0\n) and level in ('1', '2', '3')\n")
                    .append(" group by level, tid");
//            sql.append("select level,sum(road_len) from road_net, temp\n" +
//                    "where ST_Dwithin( \n" +
//                    " temp.point,\n" +
//                    " road_net.geom,\n" +
//                    "\t200.0\n" +
//                    ") and road_net.level in ('1','2','3')\n" +
//                    "group by level, tid");
            System.out.println(sql.toString());
            return sql.toString();
        }

        public String poiNameQuery(FilterConditionV2 filterConditionV2) {
            StringBuilder sql = new StringBuilder();
            sql.append("with temp as (").append(stayPointQuery(filterConditionV2)).append(")\n");
            sql.append(",sontable as (\n" +
                    "select count(distinct(temp.point)) as cnt,poi_beijing.poi_type as poi_type, poi_beijing.poi_name as poi_name, min(poi_beijing.lat) as lat, min(poi_beijing.lng) as lng\n" +
                    "from temp\n" +
                    "join poi_beijing\n" +
                    "on st_dwithin(temp.point, poi_beijing.geom, 40) \n" +
                    "where poi_beijing.poi_type != 'unknown'\n" +
                    "group by poi_beijing.poi_type, poi_beijing.poi_name\n" +
                    ")\n" +
                    "select a.* from\n" +
                    "(select sontable.poi_type, sontable.poi_name, sontable.cnt, sontable.lng, sontable.lat, rank() over (PARTITION BY sontable.poi_type order by sontable.cnt desc)\n" +
                    "from sontable) a\n" +
                    "where rank <= 10"
            );
//            System.out.println(sql.toString());
            return sql.toString();
        }
    }
        //poi、路网统计v1

//        /**
//         * poi统计
//         */
//        public String poiQuery( FilterConditions filterConditions) {
//            StringBuilder sql = new StringBuilder();
//            sql.append("with temp as (").append(queryAttribute(filterConditions)).append(")\n");
//            sql.append("\nselect poi.*\n from poi, temp\nwhere ST_Dwithin(\ntemp.point,\npoi.geom,\n50\n) and poi_type != 'unknown'");
//            return sql.toString();
//        }
//        /**
//         * 路网统计
//         */
//        public String roadQuery(List<StayPoint> stayPointList, FilterConditions filterConditions, int level) {
//            StringBuilder sql = new StringBuilder();
//            sql.append("with temp as (").append(queryAttribute(filterConditions)).append(")\n");
////            sql.append("with temp as(\n")
////                    .append("select *\n from ")
////                    .append(filterConditions.getTableName())
////                    .append("\n where tid in (");
////            for (int i = 0; i < stayPointList.size(); i++) {
////                if(i == stayPointList.size() - 1) {
////                    sql.append("'").append(stayPointList.get(i).getTid()).append("')");
////                } else {
////                    sql.append("'").append(stayPointList.get(i).getTid()).append("',");
////                }
////            }
////            sql.append(")");
//            sql.append("\nselect sum(road_len)\n from road_net, temp\nwhere ST_Dwithin(\ntemp.point,\nroad_net.geom,\n50\n) and level = ")
//                    .append(level)
//                    .append(" group by temp.tid");
//            System.out.println(sql.toString());
//            return sql.toString();
//        }}

}
