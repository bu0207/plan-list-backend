package com.bnt.plan.common;

import lombok.Data;

import java.util.HashMap;

/**
 * 通用返回类
 *
 * @param <T>
 */
@Data
public class BaseResponse<T> extends HashMap<String, Object> {

    private int code;

    private T data;

    private String message;

    public BaseResponse() {
        // 默认构造函数
    }

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
