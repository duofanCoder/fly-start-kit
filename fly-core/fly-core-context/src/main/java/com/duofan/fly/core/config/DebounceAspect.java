package com.duofan.fly.core.config;

import com.duofan.fly.core.base.domain.exception.FlyConstraintException;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.WebUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Aspect
public class DebounceAspect {
    @Resource
    private HttpServletRequest request;
    private static final long DEBOUNCE_INTERVAL = 1000; // 1秒钟内只允许一次请求

    @Resource
    private FlyCacheService cacheService;

    @Around("@annotation(com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo)")
    public Object handleDebounce(ProceedingJoinPoint joinPoint) throws Throwable {
        String userId = extractUserIdFromArguments(joinPoint.getArgs()); // 从方法参数中获取用户标识
        // 如果上次请求时间戳存在，并且与当前请求时间戳的间隔小于阈值，拒绝处理请求
        if (cacheService.hasKey(userId)) {
            throw new FlyConstraintException("请求过于频繁，请稍后再试");
        }

        // 记录当前请求的时间戳
        cacheService.set(userId, 1, Duration.ofMillis(DEBOUNCE_INTERVAL));

        // 继续执行原有的Controller方法
        return joinPoint.proceed();
    }

    private String extractUserIdFromArguments(Object[] args) {
        // 实现根据参数获取用户标识的逻辑
        // ...
        return WebUtils.getIp(request);
    }
}
