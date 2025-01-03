package com.duofan.fly.core.base.domain.exception;

/**
 * 业务夜场
 * 该异常包裹的message会原封不动返回给前端
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/4
 */
public class FlyBizException extends FlyException {
    public FlyBizException() {
    }

    public FlyBizException(String message) {
        super(message);
    }

    public FlyBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlyBizException(Throwable cause) {
        super(cause);
    }

    public FlyBizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
