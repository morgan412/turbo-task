package com.turbo.schedule.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @author zouxq
 */
@Data
public class R<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 承载数据
     */
    private T data;


    private R(ResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.getCode() == code;
    }

    public static <T> R<T> success() {
        return build(ResultCode.SUCCESS, null, null);
    }

    public static <T> R<T> success(T data) {
        return success(data, null);
    }

    public static <T> R<T> success(String msg) {
        return success(null, msg);
    }

    public static <T> R<T> success(T data, String msg) {
        return build(ResultCode.SUCCESS, data, msg);
    }

    public static <T> R<T> build(ResultCode resultCode, T data, String msg) {
        return new R<>(resultCode, data, msg == null ? resultCode.getMsg() : msg);
    }

    public static R fail(String msg) {
        return build(ResultCode.FAILURE, null, msg);
    }

}
