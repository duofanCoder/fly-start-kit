package com.duofan.fly.framework.security.exception;

/**
 * 可疑触犯安全类操作异常
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/24
 */
public class FlySuspiciousSecurityException extends FlySecurityException {

    public FlySuspiciousSecurityException() {
    }

    public FlySuspiciousSecurityException(String message) {
        super(message);
    }

    public FlySuspiciousSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlySuspiciousSecurityException(Throwable cause) {
        super(cause);
    }

    public FlySuspiciousSecurityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
