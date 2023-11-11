package com.duofan.fly.core.config;

import com.duofan.fly.core.base.domain.exception.FlyConstraintException;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

/**
 * 恶意字典接口锁定，不适用切面
 * <p>
 * 使用过滤器 {@link com.duofan.fly.framework.security.context.lock.DebounceRequestLockoutFilter}
 * 可以拦截字典
 * <p>
 */
//@Component
//@Aspect
@Deprecated
public class DebounceAspect {
    @Resource
    private HttpServletRequest request;
    // 一段时间内的访问次数
    private static final long DEBOUNCE_INTERVAL = 3 * 1000; // 3 秒内
    // 设置访问次数阈值
    private static final int DEBOUNCE_REQUEST_LIMIT = 5;
    @Resource
    private FlyCacheService cacheService;

    @Around("@annotation(com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo)")
    public Object handleDebounce(ProceedingJoinPoint joinPoint) throws Throwable {
        String debounceKey = CacheKeyUtils.getDebounceKey(request); // 从方法参数中获取用户标识
        // 如果上次请求时间戳存在，并且与当前请求时间戳的间隔小于阈值，拒绝处理请求
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - DEBOUNCE_INTERVAL; // 60秒前的时间戳
        cacheService.removeBefore(debounceKey, windowStart);
        if (cacheService.getAwhileCount(debounceKey, windowStart) > DEBOUNCE_REQUEST_LIMIT) {
            throw new FlyConstraintException("请求过于频繁，请稍后再试");
        }
        cacheService.setCurrentTime(debounceKey, currentTime);

        // 继续执行原有的Controller方法
        return joinPoint.proceed();
    }


}
