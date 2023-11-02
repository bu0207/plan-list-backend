package com.bnt.plan.common;

import cn.hutool.http.HttpStatus;

/**
 * 返回工具类
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(HttpStatus.HTTP_OK, data, "ok");
    }

    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(HttpStatus.HTTP_OK, null, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @return
     */
    public static BaseResponse error(String message) {
        return new BaseResponse(ErrorCode.ERROR_CODE.getCode(), null, message);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }
}
