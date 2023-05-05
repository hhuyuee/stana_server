package com.example.demo5.entity.condition;

import lombok.Data;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 18:39
 * @since 2.0.0
 */
@Data
public class IntersectCondition extends BaseCondition{

    Integer intersectNum;

    String intersectSymbol;

    public IntersectCondition(String type, Integer intersectNum, String intersectSymbol) {
        super(type);
        this.intersectNum = intersectNum;
        this.intersectSymbol = intersectSymbol;
    }

    public IntersectCondition() {
        super();
    }

    @Override
    public String buildSql() {
        return "\nand s.intersect_num " + intersectSymbol + intersectNum;
    }
}
