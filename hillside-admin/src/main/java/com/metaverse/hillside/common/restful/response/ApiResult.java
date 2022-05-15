package com.metaverse.hillside.common.restful.response;

import com.metaverse.hillside.common.constants.RestfulStatusEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResult<T> implements IResult<T>, Serializable {

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    private ApiResult(final String code) {
        this.code = code;
    }

    private ApiResult(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    private ApiResult(final String code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ApiResult(final RestfulStatusEnum restfulStatusEnum) {
        this.code = restfulStatusEnum.getCode();
    }

    private ApiResult(final RestfulStatusEnum restfulStatusEnum, final String message) {
        this.code = restfulStatusEnum.getCode();
        this.message = message;
    }

    private ApiResult(final RestfulStatusEnum restfulStatusEnum, final String message, final T data) {
        this.code = restfulStatusEnum.getCode();
        this.message = message;
        this.data = data;
    }

    private static <T> ApiResult<T> restful(final String code) {
        return new ApiResult<>(code);
    }

    private static <T> ApiResult<T> restful(final String code, final String message) {
        return new ApiResult<>(code, message);
    }

    private static <T> ApiResult<T> restful(final String code, final String message, final T data) {
        return new ApiResult<>(code, message, data);
    }

    private static <T> ApiResult<T> restful(final RestfulStatusEnum restfulStatusEnum) {
        return new ApiResult<>(restfulStatusEnum);
    }

    private static <T> ApiResult<T> restful(final RestfulStatusEnum restfulStatusEnum, final String message) {
        return new ApiResult<>(restfulStatusEnum, message);
    }

    private static <T> ApiResult<T> restful(final RestfulStatusEnum restfulStatusEnum, final String message, final T data) {
        return new ApiResult<>(restfulStatusEnum, message, data);
    }

    public static <T> ApiResult<T> failed() {
        return restful(RestfulStatusEnum.FAILED);
    }

    public static <T> ApiResult<T> failed(final String message) {
        return restful(RestfulStatusEnum.FAILED, message);
    }

    public static <T> ApiResult<T> failed(final String message, final T data) {
        return restful(RestfulStatusEnum.FAILED, message, data);
    }

    public static <T> ApiResult<T> succeeded() {
        return restful(RestfulStatusEnum.SUCCEED);
    }

    public static <T> ApiResult<T> succeeded(final String message) {
        return restful(RestfulStatusEnum.SUCCEED, message);
    }

    public static <T> ApiResult<T> succeeded(final String message, final T data) {
        return restful(RestfulStatusEnum.SUCCEED, message, data);
    }

    public static <T> ApiResult<T> warned() {
        return restful(RestfulStatusEnum.WARNED);
    }

    public static <T> ApiResult<T> warned(final String message) {
        return restful(RestfulStatusEnum.WARNED, message);
    }

    public static <T> ApiResult<T> warned(final String message, final T data) {
        return restful(RestfulStatusEnum.WARNED, message, data);
    }

}

