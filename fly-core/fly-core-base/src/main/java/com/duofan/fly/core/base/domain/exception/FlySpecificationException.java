package com.duofan.fly.core.base.domain.exception;

/**
 * 不符合fly-boot框架规范，抛出一场
 */
public class FlySpecificationException extends FlyException{
    public FlySpecificationException() {
        super("框架规范异常，请修改代码");
    }

    public FlySpecificationException(String message) {
        super(message);
    }

    public FlySpecificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlySpecificationException(Throwable cause) {
        super(cause);
    }

    public FlySpecificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
