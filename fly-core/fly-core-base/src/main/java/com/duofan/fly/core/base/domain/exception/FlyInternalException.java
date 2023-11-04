package com.duofan.fly.core.base.domain.exception;

/**
 * 服务器内部错误异常
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/4
 */
public class FlyInternalException extends FlyException {
    public FlyInternalException() {
    }

    public FlyInternalException(String message) {
        super(message);
    }

    public FlyInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlyInternalException(Throwable cause) {
        super(cause);
    }

    public FlyInternalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
