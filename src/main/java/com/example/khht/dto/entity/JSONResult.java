package com.example.khht.dto.entity;

/**
 * JSON Result
 */
public class JSONResult<T> {

    /**
     * 返回数据
     */
    private T data;

    /**
     * 消息
     */
    private String message;

    public JSONResult() {
    }

    public JSONResult(T data, String message) {
        this.data = data;
        this.message = message;
    }

    /**
     * 返回 返回数据
     */
    public T getData() {
        return data;
    }

    /**
     * 设置 返回数据
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 返回 消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置 消息
     */
    public void setMessage(String message) {
        this.message = message;
    }
}