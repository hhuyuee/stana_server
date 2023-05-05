package com.example.demo5.common;

import lombok.Data;

/**
 *
 * @author huyue87
 * @date 2020-11-07 15:01 2020-11-07 23:17
 * 返回响应结果
 */
@Data
public class ResponseResult<T> { // 泛型T表示数据的类型
    /**
     * 相应的状态码
     */
    private int resultCode;
    /**
     * 返回信息 主要是错误信息提示
     */
    private String resultMsg;
    /**
     * 当前时间戳
     */
    private Long tid;
    /**
     * 传递的数据
     */
    private T data;

    /**
     * 构造函数1: 传递正确
     * @param status 状态码
     * @param data 数据
     */
    public ResponseResult(int status,T data){
        this(status, null,data);
    }

    /**
     * 传递出现异常
     * @param status 状态码
     * @param message 错误信息
     * @param data 数据
     */
    public ResponseResult(int status, String message,T data){
        this.resultCode = status;
        this.resultMsg = message;
        this.tid = System.currentTimeMillis();
        this.data = data;
    }

}
