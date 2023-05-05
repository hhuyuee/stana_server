package com.example.demo5.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 驻留点数据集
 *
 * @author huyue87@jd.com
 * @date 2020/12/4 10:13
 * @since 2.0.0
 */
@Data
public class Dataset {
    /**
     * 数据集名称
     */
    @ApiModelProperty(value = "轨迹数据集", example = "")
    String datesetName;
    /**
     * 距离阈值
     */
    @ApiModelProperty(value = "驻留点距离阈值", example = "")
    double distance;
    /**
     * 时间阈值
     */
    @ApiModelProperty(value = "驻留点时间阈值", example = "")
    double interval;
}
