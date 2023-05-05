package com.example.demo5.entity.condition;

import lombok.Data;

import java.util.List;

/**
 * 路网条件
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 16:16
 * @since 2.0.0
 */
@Data
public class RoadCondition extends BaseCondition{
    List<String> roadType;
    Double roadScope;
    Integer roadFilter;

    public RoadCondition() {
        super();
    }

    /**
     * 构造函数
     */
    public RoadCondition(String type, List<String> roadType, Double roadScope, Integer roadFilter) {
        super(type);
        this.roadType = roadType;
        this.roadScope = roadScope;
        this.roadFilter = roadFilter;
    }
    /**
     * 拼接sql
     */
    @Override
    public String buildSql(int tableSequence, String roadTable) {
        StringBuilder sql = new StringBuilder();
        String roadGeom = roadTable + ".geom";
        String roadLevel = roadTable + ".level";
//        sql.append("\nintersect");
        sql.append("\ntemp" + (tableSequence + 1) + " as (");
        // 范围外筛选
        if(roadFilter == 0) {
            sql.append("\nselect s.tid, s.oid, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num, s.week\nfrom ")
                    .append("temp" + tableSequence)
                    .append(" as s")
                    .append("\nexcept");
        }
        sql.append("\nselect distinct s.tid, s.oid, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num, s.week\nfrom ")
                .append("temp" + tableSequence)
                .append(" as s, ")
                .append(roadTable)
                .append("\nwhere ST_Dwithin(\n s.point,\n")
                .append(roadGeom)
                .append(",\n")
                .append(this.roadScope)
                .append(")\nand ")
                .append(roadLevel)
                .append(" in (");
        StringBuilder tempRoad = new StringBuilder();
//        List<String> rTypeList = NumUtils.string2List(this.roadType);
        for (int i = 0; i < roadType.size(); i++) {
            if(i != roadType.size() - 1) {
                tempRoad.append("'").append(roadType.get(i)).append("'").append(",");
            } else {
                tempRoad.append("'").append(roadType.get(i)).append("'").append(")");
            }
        }
        sql.append(tempRoad).append(" ),");
        return sql.toString();
    }

    @Override
    public String buildSql(String tableName, String roadTable) {
        StringBuilder sql = new StringBuilder();
        String roadGeom = roadTable + ".geom";
        String roadLevel = roadTable + ".level";
        // 范围外筛选
        if(roadFilter == 0) {
            sql.append("\nselect s.tid, s.oid, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num, s.week\nfrom ")
                    .append(tableName)
                    .append(" as s")
                    .append("\nexcept");
        }
        sql.append("\nselect distinct s.tid, s.oid, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num, s.week\nfrom ")
                .append(tableName)
                .append(" as s, ")
                .append(roadTable)
                .append("\nwhere ST_Dwithin(\n s.point,\n")
                .append(roadGeom)
                .append(",\n")
                .append(this.roadScope)
                .append(")\nand ")
                .append(roadLevel)
                .append(" in (");
        StringBuilder tempRoad = new StringBuilder();
//        List<String> rTypeList = NumUtils.string2List(this.roadType);
        for (int i = 0; i < roadType.size(); i++) {
            if(i != roadType.size() - 1) {
                tempRoad.append("'").append(roadType.get(i)).append("'").append(",");
            } else {
                tempRoad.append("'").append(roadType.get(i)).append("'").append(")");
            }
        }
        sql.append(tempRoad);
        return sql.toString();
    }
}
