package com.duofan.fly.validate.aop;

import com.duofan.fly.core.base.domain.exception.FlyAccessVerifyException;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import com.duofan.fly.validate.constraint.api.FlyAccessResourceVerification;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * 验证信息上下文的切面
 */
@Aspect
@Component
@Slf4j
public class AccessVerificationAspect {

    @Resource
    private FlyCacheService cacheService;

    @Resource
    private HttpServletRequest request;

    @Around("@annotation(com.duofan.fly.validate.constraint.api.FlyAccessResourceVerification)")
    public Object verifyAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        String cacheKey = CacheKeyUtils.getVerifyCacheKey(request, false);
        if (cacheService.hasKeyThenDelete(cacheKey)) {
            return joinPoint.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        // 获取方法上的注解
        FlyAccessResourceVerification annotation = method.getAnnotation(FlyAccessResourceVerification.class);

        // 敏感资源访问，超过次数封锁
        verifyErrorCount(annotation);

        // 验证失败，可以抛出伪造异常
        throw new FlyAccessVerifyException(annotation.fakeCode(), annotation.fakeMessage());
    }


    // 验证失败次数保存，超过次数封锁IP
    private void verifyErrorCount(FlyAccessResourceVerification annotation) {
        String resourceLockCacheKey = CacheKeyUtils.getResourceLockCacheKey(request);
        int maxErrorCount = annotation.maxErrorCount();
        long increment = cacheService.increment(resourceLockCacheKey, 1, 1, Duration.ofSeconds(annotation.limitTime()));
        if (increment > maxErrorCount) {
            // 封锁IP
            cacheService.expire(resourceLockCacheKey, Duration.ofSeconds(annotation.limitTime()));
        }
    }

}
