package com.example.demo5.entity.condition;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 18:24
 * @since 2.0.0
 */

import lombok.Data;

@Data
public class TimeScopeCondition extends BaseCondition{
    String sTime;

    public TimeScopeCondition() {
        super();
    }

    String eTime;

    public TimeScopeCondition(String type, String sTime, String eTime) {
        super(type);
        this.eTime = eTime;
        this.sTime = sTime;
    }
    @Override
    public String buildSql() {
        StringBuilder sql = new StringBuilder();
        if (sTime != null) {
            sql.append("\nand s.mid_time > '").append(sTime).append("'");
        }
        if (eTime != null) {
            sql.append("\nand s.mid_time < '").append(eTime).append("'");
        }
        return sql.toString();
    }

}
