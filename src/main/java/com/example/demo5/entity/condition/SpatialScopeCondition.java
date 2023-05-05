package com.example.demo5.entity.condition;

import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 15:16
 * @since 2.0.0
 */
@Data
public class SpatialScopeCondition extends BaseCondition {
    /**
     * 条件参数
     */
    List<String> mbrStringList;

    /**
     * 构造函数
     */
    public SpatialScopeCondition(String type, List<String> mbrStringList) {
        super(type);
        this.mbrStringList = mbrStringList;
    }

    public SpatialScopeCondition() {
        super();
    }

    @Override
    public String buildSql() {
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < mbrStringList.size(); i++) {
            if (i == 0) {
                sql.append("\nand st_contains(st_transform(st_geomfromtext('").append(mbrStringList.get(i)).append("',4326), 3857), s.point)");
            } else {
                sql.append("\nor st_contains(st_transform(st_geomfromtext('").append(mbrStringList.get(i)).append("',4326), 3857), s.point)");
            }
        }
        return sql.toString();
    }
}


