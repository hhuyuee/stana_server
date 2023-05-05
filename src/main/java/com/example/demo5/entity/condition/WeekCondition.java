package com.example.demo5.entity.condition;

import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 19:49
 * @since 2.0.0
 */
@Data
public class WeekCondition extends BaseCondition{


    List<String> weekList;

    public WeekCondition(String type, List<String> weekList) {
        super(type);
        this.weekList = weekList;
    }

    @Override
    public String buildSql() {
        StringBuilder sql = new StringBuilder();
        sql.append("\nand s.week in (");
        StringBuilder tempSeq = new StringBuilder();
        for (int i = 0; i < weekList.size(); i++) {
            if(i != weekList.size() - 1) {
                tempSeq.append("'").append(weekList.get(i)).append("',");
            } else {
                tempSeq.append("'").append(weekList.get(i)).append("')");
            }
        }
        sql.append(tempSeq);
        return sql.toString();
    }

    public WeekCondition() {
        super();
    }
}
