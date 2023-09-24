package com.duofan.fly.framework.security.exception;

import com.duofan.fly.core.base.domain.exception.FlyException;

/**
 * 安全相关异常
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/24
 */
public class FlySecurityException extends FlyException {
    public FlySecurityException() {
    }

    public FlySecurityException(String message) {
        super(message);
    }

    public FlySecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlySecurityException(Throwable cause) {
        super(cause);
    }

    public FlySecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
