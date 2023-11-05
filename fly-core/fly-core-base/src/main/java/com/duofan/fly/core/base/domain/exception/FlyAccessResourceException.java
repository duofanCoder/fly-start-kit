package com.duofan.fly.core.base.domain.exception;

/**
 * 访问敏感资源异常
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/5
 */
public class FlyAccessResourceException extends FlyException {
    public FlyAccessResourceException() {
    }

    public FlyAccessResourceException(String message) {
        super(message);
    }

    public FlyAccessResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlyAccessResourceException(Throwable cause) {
        super(cause);
    }

    public FlyAccessResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
