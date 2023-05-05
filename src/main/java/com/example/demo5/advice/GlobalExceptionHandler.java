package com.example.demo5.advice;

import com.example.demo5.common.ResponseResult;
import com.example.demo5.common.StatusCode;
import com.example.demo5.constant.Constant;
import com.example.demo5.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-19 18:42
 * 全局
 */
//默认拦截所有的controller
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 自定义异常处理
     * @param e 自定义异常
     * @return ResponseResult<T>
     */
    @ExceptionHandler(AppException.class)
    public ResponseResult customExceptionHandler(AppException e){
        log.debug("自定义异常", e.getMessage());
        return new ResponseResult(e.getStatus(),e.getMessage(),null);
    }

    /**
     * 参数校验异常处理
     * @param e 参数校验异常
     * @return ResponseResult<T>
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult bindExceptionHandler(BindException e){
        log.debug("参数校验异常",e);
        BindingResult bindingResult = e.getBindingResult();
        //如果有捕获到参数不合法
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            //得到全部不合法的字段
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            //遍历不合法字段
            for (FieldError fieldError: fieldErrors) {
                //获取不合法的字段名和错误提示
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }

        }
        return new ResponseResult(StatusCode.BAD_REQUEST, Constant.ILLEGAL_PARAM_ERROR,map);
    }

    /**
     * 系统异常处理
     * @param e 系统异常
     * @return ResponseResult<T>
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        log.error("捕获到Exception异常",e);
        ResponseResult rr = null;
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            rr = new ResponseResult(StatusCode.NOT_FOUND,Constant.NOT_FOUND,e.getMessage());
        } else {
            rr = new ResponseResult(StatusCode.SERVER_ERROR,Constant.SERVER_ERROR,e.getMessage());
        }
        return rr;
    }
}
