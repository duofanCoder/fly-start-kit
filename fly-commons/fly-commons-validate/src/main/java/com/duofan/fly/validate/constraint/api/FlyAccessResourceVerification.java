package com.duofan.fly.validate.constraint.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FlyAccessResourceVerification {
    // 伪造访问出错的错误信息
    String fakeMessage() default "访问太快,请稍后再试";

    // 伪造访问出错的错误码
    String fakeCode() default "200";
}
