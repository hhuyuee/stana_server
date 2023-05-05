package com.example.demo5.entity;

import com.example.demo5.entity.vo.CandidatePlaceVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 地点信息统计（返回前端）
 *
 * @author huyue87@jd.com
 * @date 2020/12/4 11:32
 * @since 2.0.0
 */
@Data
@Accessors(chain = true)
public class PlaceInfo {
    // 包括每一个地点单个的信息和候选地点的统计信息
    /**
     * 候选地点集合
     */
    List<CandidatePlaceVo> candidatePlaceVos;
    /**
     * 地点数目
     */
    Integer placeNum;
    /**
     * 表格数据
     */
    ChartData3 chartData;
}
