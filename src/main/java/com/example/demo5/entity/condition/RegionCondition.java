package com.example.demo5.entity.condition;

import lombok.Data;

import java.util.List;

/**
 * 行政区划条件
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 18:29
 * @since 2.0.0
 */
@Data
public class RegionCondition extends BaseCondition{

    List<String> regionList;
    public RegionCondition(String type, List<String> regionList) {
        super(type);
        this.regionList = regionList;
    }

    public RegionCondition() {
        super();
    }

    @Override
    public String buildSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("and s.region in (");
//        List<String> regionList = NumUtils.string2List(regionStr);
        StringBuilder tempRegion = new StringBuilder();
        for (int i = 0; i < regionList.size(); i++) {
            if(i != regionList.size() - 1) {
                tempRegion.append("'").append(regionList.get(i)).append("'").append(",");
            } else {
                tempRegion.append("'").append(regionList.get(i)).append("'").append(")");
            }
        }
        sql.append(tempRegion);
        return sql.toString();
    }
}
