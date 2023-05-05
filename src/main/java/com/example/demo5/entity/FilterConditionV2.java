package com.example.demo5.entity;

import com.example.demo5.entity.condition.BaseCondition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 筛选条件重构第二版
 *
 * @author huyue87@jd.com
 * @date 2020/12/8 18:04
 * @since 2.0.0
 */
@ApiModel("驻留点-筛选-参数v2")
@Data
public class FilterConditionV2 {
    /**
     * 查询的表格名(不为空)
     */
    @ApiModelProperty(value = "表名", example = "staypoint_15_100", required = true)
    String tableName;

    /**
     * 参数
     */
    List<BaseCondition> conditionList;
}
