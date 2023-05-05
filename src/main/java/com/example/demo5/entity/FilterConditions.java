package com.example.demo5.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 驻留点的单个表查询
 *
 * @author huyue87@jd.com
 * @since 2.0.0
 * @date 2020/11/28 19:40
 */
@ApiModel("驻留点-筛选-参数")
@Data
public class FilterConditions {
    /**
     * 查询的表格名(不为空)
     */
    @ApiModelProperty(value = "表名", example = "staypoint_15_100", required = true)
    String tableName;

    @ApiModelProperty(value = "类型", example = "poi", required = true)
    String type;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "起始时间", example = "2010-08-11 12:00:00")
    String sTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间", example = "2010-08-11 12:00:00")
    String eTime;
    /**
     * 空间范围 支持或查询多条件
     */
    @ApiModelProperty(value = "空间范围查询", example = "POLYGON((116.34368 39.96596,116.34368 39.96672,116.34419 39.96672,116.34419 39.96596,116.34368 39.96596))")
    String mbrStringStr;

    /**
     * 行政区划查询
     */
    @ApiModelProperty(value = "行政区划查询", example = "通州区，大兴区")
    String regionStr;
    /**
     * 停留时长的范围界定（大于、小于等于）
     */
    @ApiModelProperty(value = "停留时长界限", example = ">")
    String durationSymbol;

    /**
     * 停留时长查询
     */
    @ApiModelProperty(value = "停留时长查询", example = "1000", dataType = "Double")
    Double duration;
    /**
     * poi 查询距离1
     */
    @ApiModelProperty(value = "poi距离", example = "100")
    Double disPoi;
    /**
     * poi 类别
     */
    @ApiModelProperty(value = "poi类别", example = "停车场，超市商场")
    String pType;

    /**
     * poi 范围内外
     */
    @ApiModelProperty(value = "poi范围指标（1 范围内，0范围外）", example = "1")
    Integer poiFilter;
    /**
     * 路网 查询距离2
     */
    @ApiModelProperty(value = "路网距离", example = "100")
    Double disRoad;
    /**
     * 路网等级
     */
    @ApiModelProperty(value = "路网距离", example = "1,2,3")
    String rType;
    /**
     * poi 范围内外
     */
    @ApiModelProperty(value = "road范围指标（1 范围内，0范围外）", example = "1")
    Integer roadFilter;

    /**
     * 交叉路口数目
     */
    @ApiModelProperty(value = "交叉路口数目", example = "3")
    Integer intersectionNum;

    /**
     * 交叉路口谓词
     */
    @ApiModelProperty(value = "交叉路口条件", example = ">")
    String intersectSymbol;


    /**
     * 周一到周日 (对应1-7 1#2#3#4)
     */
    @ApiModelProperty(value = "星期查询", example = "")
    String weekDaysStr;

    /**
     * 每天的时间段
     */
    @ApiModelProperty(value = "时间段查询", example = "")
    String timeSequencesStr;
}
