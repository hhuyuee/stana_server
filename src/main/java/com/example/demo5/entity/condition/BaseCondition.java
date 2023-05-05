package com.example.demo5.entity.condition;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 条件的抽象类
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 14:17
 * @since 2.0.0
 */
@Data
@Accessors(chain = true)
public class BaseCondition {
    /**
     * 筛选唯一表示
     */
    String type;
    public BaseCondition() {}
    public BaseCondition(String type) {
        this.type = type;
    }
    public String buildSql() {
        return null;
    }
    public String buildSql(int tableSequence) {
        return null;
    }
    public String buildSql(int tableSequence, String tableName) {
        return null;
    }
    public String buildSql(String tableName) {
        return null;
    }
    public String buildSql(String tableName1, String tableName2) {
        return null;
    }

}
