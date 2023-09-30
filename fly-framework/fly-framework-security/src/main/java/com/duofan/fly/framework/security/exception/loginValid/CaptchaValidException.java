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
public class CaptchaValidException extends LoginValidException {

    public CaptchaValidException() {
    }

    public CaptchaValidException(String message) {
        super(message);
    }

    public CaptchaValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaValidException(Throwable cause) {
        super(cause);
    }

    public CaptchaValidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
