package com.example.demo5.controller;

import com.example.demo5.common.ResponseResult;
import com.example.demo5.entity.StayPointInfo;
import com.example.demo5.service.StayPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 预处理阶段的全表驻留点显示和统计查询
 *
 * @author huyue87@jd.com
 * @date 2020/11/30 10:27
 * @since 2.0.0
 */

@Api(tags = "数据集的选择和全量数据统计结果")
@RestController
//@CrossOrigin
public class PreController {


    @Autowired
    StayPointService stayPointService;

    @ApiOperation("数据集选择和全量数据统计结果")
    @PostMapping("/fullstaypoint")
    public ResponseResult<StayPointInfo> selectDataTable(
            @ApiParam(required = true, value = "tablename",example = "staypoint_15_50", type = "String")
            @RequestParam(required = true) String tableName) {
        StayPointInfo stayPointInfo = stayPointService.searchAll(tableName);
        return new ResponseResult(200, stayPointInfo);
    }
}
