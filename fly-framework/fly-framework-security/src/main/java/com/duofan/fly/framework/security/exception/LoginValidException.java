package com.duofan.fly.framework.security.exception;

import com.duofan.fly.core.base.domain.exception.FlyException;

/**
 * 自定义校验异常
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
public class LoginValidException extends FlyException {
    public LoginValidException() {
    }

    public LoginValidException(String message) {
        super(message);
    }

    public LoginValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginValidException(Throwable cause) {
        super(cause);
    }

    public LoginValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
