package com.metaverse.hillside.common.exception;

/**
 * 校验 系统启动、运行缺少必要参数等问题 错误
 */
public class SystemError extends Error {

    public SystemError() {
        super();
    }

    public SystemError(String message) {
        super(message);
    }

    public SystemError(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemError(Throwable cause) {
        super(cause);
    }

    protected SystemError(String message, Throwable cause,
                          boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
