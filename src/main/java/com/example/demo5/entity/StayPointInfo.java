package com.example.demo5.entity;

import com.example.demo5.entity.vo.PoiVo;
import com.example.demo5.entity.vo.StayPointVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用于前后端交互的staypoint信息传递
 *
 * @author huyue87@jd.com
 * @date 2020/11/30 13:21
 * @since 2.0.0
 */
@Data
@Accessors(chain = true)
public class StayPointInfo {
    /**
     * staypoint 列表
     */
    List<StayPointVo> stayPointVos;


    /***** staypoint 列表的统计信息 ****/
    /**
     * 总访问数 -> 驻留点列表的数目
     */
    int visitNum;
    /**
     * 时间范围 -> 驻留点开始的最早时间
     */
    String startTime;
    /**
     * 时间范围 -> 驻留点开始的最晚时间
     */
    String endTime;

    /**
     * poi name分布 (type-> list)
     */
    List<PoiVo> poiNameTop;

    /**
     * 统计表格数据
     */
    ChartData1 chartData;
}
