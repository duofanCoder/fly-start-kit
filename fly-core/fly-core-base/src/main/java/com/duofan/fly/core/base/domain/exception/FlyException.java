package com.duofan.fly.core.base.domain.exception;

/**
 * 基本异常
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/22
 */
public class FlyException extends RuntimeException {
    public FlyException() {
    }

    public FlyException(String message) {
        super(message);
    }

    public FlyException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlyException(Throwable cause) {
        super(cause);
    }

    public FlyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
