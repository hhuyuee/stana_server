package com.example.demo5.entity.condition;

import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 19:53
 * @since 2.0.0
 */
@Data
public class TimeSegCondition extends BaseCondition{
    List<String> timeSegList;

    public TimeSegCondition(String type, List<String> timeSegList) {
        super(type);
        this.timeSegList = timeSegList;
    }

    public TimeSegCondition() {
        super();
    }

    @Override
    public String buildSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("\nand substring(mid_time,12,2) in (");
        StringBuilder tempSeq = new StringBuilder();
        for (int i = 0; i < timeSegList.size(); i++) {
            if(i != timeSegList.size() - 1) {
                tempSeq.append("'").append(timeSegList.get(i)).append("',");
            } else {
                tempSeq.append("'").append(timeSegList.get(i)).append("')");
            }
        }
        sql.append(tempSeq);
        return sql.toString();
    }
}
