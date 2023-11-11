package com.duofan.fly.framework.security.context.lock;

import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.enums.web.FlyHttpStatus;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import com.duofan.fly.core.utils.WebUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 恶意字典接口锁定
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/11
 */
public class DebounceRequestLockoutFilter extends OncePerRequestFilter {


    // 一段时间内的访问次数
    private static final long DEBOUNCE_INTERVAL = 3 * 1000; // 3 秒内
    // 设置访问次数阈值
    private static final int DEBOUNCE_REQUEST_LIMIT = 5;
    private final FlyCacheService cacheService;

    public DebounceRequestLockoutFilter(FlyCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String debounceKey = CacheKeyUtils.getDebounceKey(request); // 从方法参数中获取用户标识
        // 如果上次请求时间戳存在，并且与当前请求时间戳的间隔小于阈值，拒绝处理请求
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - DEBOUNCE_INTERVAL; // 60秒前的时间戳
        long awhileCount = cacheService.getAwhileCount(debounceKey, windowStart);
        System.out.println(awhileCount);
        if (awhileCount > DEBOUNCE_REQUEST_LIMIT) {
            WebUtils.responseJson(response, FlyResult.of(FlyHttpStatus.TOO_MANY_REQUESTS));
            return;
        }

        cacheService.setCurrentTime(debounceKey, currentTime);
        filterChain.doFilter(request, response);
    }
}
