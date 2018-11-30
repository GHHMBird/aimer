package com.buyint.mergerbot.dto;

import java.io.Serializable;

/**
 * created by licheng  on date 2018/8/9
 */
public class ResultBean<T> implements Serializable {

    private String code;

    private T data;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
