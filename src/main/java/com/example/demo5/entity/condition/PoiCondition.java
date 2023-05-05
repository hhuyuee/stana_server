package com.example.demo5.entity.condition;

import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 11:30
 * @since 2.0.0
 */
@Data
public class PoiCondition extends BaseCondition {
    List<String> poiType;
    Double poiScope;
    Integer poiFilter;

    /**
     * 无参构造
     */
    public PoiCondition() {
        super();

    }

    /**
     * 构造函数
     */
    public PoiCondition(String type, List<String> poiType, Double poiScope, Integer poiFilter) {
        super(type);
        this.poiType = poiType;
        this.poiScope = poiScope;
        this.poiFilter = poiFilter;
    }

    /**
     * 拼接sql
     */
    @Override
    public String buildSql(int tableSequence, String poiTable) {
        StringBuilder sql = new StringBuilder();
        String poiGeom = poiTable + ".geom";
        String poiTableType = poiTable + ".poi_type";
//         sql.append("\nintersect");
        sql.append("\ntemp" + (tableSequence + 1) + " as (");
        // 范围外筛选
        if (poiFilter == 0) {
            sql.append("\nselect s.tid, s.oid, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num, s.week\nfrom ")
                    .append("temp" + tableSequence)
                    .append(" as s")
                    .append("\nexcept");
        }
        sql.append("\nselect distinct s.tid, s.oid, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num, s.week\nfrom ")
                .append("temp" + tableSequence)
                .append(" as s, ")
                .append(poiTable)
                .append("\nwhere ST_Dwithin(\n s.point,\n")
                .append(poiGeom)
                .append(",\n")
                .append(this.poiScope).append(")\n");
//        System.out.println(poiType.size());
        if (poiType.size() == 1 && poiType.get(0).equals("")) {
            sql.append(" ),");
            return sql.toString();
        }
        sql.append("and ")
                .append(poiTableType)
                .append(" in (");
        StringBuilder tempPoi = new StringBuilder();
//        List<String> pTypeList = NumUtils.string2List(this.poiType);
        for (int i = 0; i < poiType.size(); i++) {
            if (i != poiType.size() - 1) {
                tempPoi.append("'").append(poiType.get(i)).append("'").append(",");
            } else {
                tempPoi.append("'").append(poiType.get(i)).append("'").append(")");
            }
        }
        sql.append(tempPoi).append(" ),");
        return sql.toString();
    }

    @Override
    public String buildSql(String tableName, String poiTable) {
        StringBuilder sql = new StringBuilder();
        String poiGeom = poiTable + ".geom";
        String poiTableType = poiTable + ".poi_type";
        if (poiFilter == 0) {
            sql.append("\nselect s.tid, s.oid, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num, s.week\nfrom ")
                    .append(tableName)
                    .append(" as s")
                    .append("\nexcept");
        }
        sql.append("\nselect distinct s.tid, s.oid, s.mid_time, st_astext(s.centroid) as centroid, s.lng, s.lat, s.duration, s.region, s.point, s.intersect_num, s.week\nfrom ")
                .append(tableName)
                .append(" as s, ")
                .append(poiTable)
                .append("\nwhere ST_Dwithin(\n s.point,\n")
                .append(poiGeom)
                .append(",\n")
                .append(this.poiScope).append(")\n");
        if (poiType.size() == 1 && poiType.get(0).equals("")) {
//            sql.append(")");
            return sql.toString();
        }
        sql.append("and ")
                .append(poiTableType)
                .append(" in (");
        StringBuilder tempPoi = new StringBuilder();
        for (int i = 0; i < poiType.size(); i++) {
            if (i != poiType.size() - 1) {
                tempPoi.append("'").append(poiType.get(i)).append("'").append(",");
            } else {
                tempPoi.append("'").append(poiType.get(i)).append("'").append(")");
            }
        }
        sql.append(tempPoi);
        return sql.toString();
    }
}
