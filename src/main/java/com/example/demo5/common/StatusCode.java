package com.example.demo5.common;

/**
 *
 * @author huyue87
 * @date 2020-11-07 15:01 2020-11-09 10:15
 * 状态码常量的设置
 */
public class StatusCode {
    /**
     * 正常访问
     */
    public static final int SUCCESS = 200;
    /**
     * 服务器异常
     */
    public static final int SERVER_ERROR = 500;
    /**
     * 请求报文出现语法错误
     */
    public static final int BAD_REQUEST = 400;
    /**
     * 服务器没有请求的资源
     */
    public static final int NOT_FOUND = 404;
}
