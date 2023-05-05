package com.example.demo5.entity.condition;

import lombok.Data;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 18:35
 * @since 2.0.0
 */
@Data
public class DurationCondition extends BaseCondition{
    Double duration;
    String durationSymbol;

    public DurationCondition() {
        super();
    }

    public DurationCondition(String type, Double duration, String durationSymbol) {
        super(type);
        this.duration = duration;
        this.durationSymbol = durationSymbol;
    }

    @Override
    public String buildSql() {
        return "\nand s.duration " + durationSymbol + duration * 60;
    }
}
