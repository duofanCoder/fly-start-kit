package com.duofan.fly.framework.security.context.lock.aop;

import com.duofan.fly.core.base.domain.exception.FlyAccessVerifyException;
import com.duofan.fly.core.spi.FlyAccessResourceVerification;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import com.duofan.fly.core.utils.WebUtils;
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
@Slf4j
@Aspect
@Component
// 加上以下注解 因为没有生成这个Bean 会导致切面失效，
// 但实际是有这个bean的
//@ConditionalOnBean(FlyCacheService.class)
public class AccessVerificationAspect {

    @Resource
    private FlyCacheService cacheService;

    @Resource
    private HttpServletRequest request;

    @Around("@annotation(com.duofan.fly.core.spi.FlyAccessResourceVerification)")
    public Object verifyAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 获取方法上的注解
        FlyAccessResourceVerification annotation = method.getAnnotation(FlyAccessResourceVerification.class);

        String cacheKey = CacheKeyUtils.getVerifyCacheKey(request, false, annotation.verificationLevel());
        if (cacheService.hasKeyThenDelete(cacheKey)) {
            
            return joinPoint.proceed();
        }
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
        log.warn("敏感资源非法访问【{}】IP:【{}】访问次数:【{}】", annotation.name(), WebUtils.getIp(request), increment);
        if (increment > maxErrorCount) {
            // 封锁IP
            cacheService.expire(resourceLockCacheKey, Duration.ofSeconds(annotation.limitTime()));
        }
    }

}
