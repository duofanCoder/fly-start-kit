package com.duofan.fly.framework.security.exception.loginValid;

import com.duofan.fly.framework.security.exception.LoginValidException;

/**
 * 验证码检查异常
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/28
 */
public class FlyCaptchaValidException extends LoginValidException {

    public FlyCaptchaValidException() {
    }

    public FlyCaptchaValidException(String message) {
        super(message);
    }

    public FlyCaptchaValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlyCaptchaValidException(Throwable cause) {
        super(cause);
    }

    public FlyCaptchaValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
