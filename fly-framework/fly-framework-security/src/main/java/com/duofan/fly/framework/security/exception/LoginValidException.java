package com.duofan.fly.framework.security.exception;

import com.duofan.fly.core.base.domain.exception.FlySecurityException;

/**
 * 自定义校验异常
 * 自定义组件实现校验组件，校验失败抛出继承该异常或其子类
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
public class LoginValidException extends FlySecurityException {
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
