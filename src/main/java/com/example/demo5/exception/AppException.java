package com.example.demo5.exception;

import lombok.Data;


/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-19 18:44
 * custom exception
 */
@Data
public class AppException extends RuntimeException{

    private int status;
    private String message;

    public AppException(int status, String message){
        this.status = status;
        this.message = message;
    }
}
