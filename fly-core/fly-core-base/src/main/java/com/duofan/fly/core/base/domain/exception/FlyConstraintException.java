package com.duofan.fly.core.base.domain.exception;

/**
 * 约束异常
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/4
 */
public class FlyConstraintException extends FlyException {
    public FlyConstraintException() {
    }

    public FlyConstraintException(String message) {
        super(message);
    }

    public FlyConstraintException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlyConstraintException(Throwable cause) {
        super(cause);
    }

    public FlyConstraintException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
