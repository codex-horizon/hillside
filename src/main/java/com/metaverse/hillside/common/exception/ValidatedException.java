package com.metaverse.hillside.common.exception;

/**
 * 校验 接口入参 异常
 */
public class ValidatedException extends RuntimeException {

    public ValidatedException() {
        super();
    }

    public ValidatedException(String message) {
        super(message);
    }

    public ValidatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatedException(Throwable cause) {
        super(cause);
    }

    public ValidatedException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

