package com.duofan.fly.core.base.domain.exception;

import lombok.Getter;

/**
 * 校验异常
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/5
 */
@Getter
public class FlyAccessVerifyException extends FlyException {
    private String code;

    public FlyAccessVerifyException() {
    }

    public FlyAccessVerifyException(String message) {
        super(message);
    }

    public FlyAccessVerifyException(String code, String message) {
        super(message);
        this.code = code;
    }

    public FlyAccessVerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlyAccessVerifyException(Throwable cause) {
        super(cause);
    }

    public FlyAccessVerifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public void setCode(String code) {
        this.code = code;
    }
}
