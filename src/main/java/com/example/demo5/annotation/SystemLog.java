package com.example.demo5.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-19 18:40
 * 日志註解類
 */
@Target(ElementType.METHOD)//作用于方法上，注解作用的范围
@Retention(RetentionPolicy.RUNTIME) //注解的注解，表 示注解的生命周期
public @interface SystemLog {

    /**
     * 模块名称  如：用户管理
     */
    String module() default "";

    /**
     * 操作类型   如：添加用户、删除用户
     */
    String operationType() default "";
}
