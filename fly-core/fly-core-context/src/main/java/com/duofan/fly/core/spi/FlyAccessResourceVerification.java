package com.duofan.fly.core.spi;

import com.duofan.fly.core.base.constant.security.AccessSensitiveResourceConstrainedTarget;
import com.duofan.fly.core.base.constant.security.FlyVerificationLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 敏感资加验证操作
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FlyAccessResourceVerification {
    // 伪造访问出错的错误信息
    String fakeMessage() default "访问太快,请稍后再试";

    String name() default "资源名称";

    // 伪造访问出错的错误码
    String fakeCode() default "500";

    // 恶意封锁
    // 是否开启IP限制
    boolean limitOpen() default true;

    // 对于恶意访问的请求直接封锁IP
    // 配置ip封锁时间 单位秒 默认封锁一天
    int limitTime() default 60 * 60 * 24;

    // 最大出错次数
    int maxErrorCount() default 5;

    FlyVerificationLevel verificationLevel() default FlyVerificationLevel.CAPTCHA;

    // 封锁维度 ip或者接口
    AccessSensitiveResourceConstrainedTarget lockTarget() default AccessSensitiveResourceConstrainedTarget.LIMIT_API;

}
