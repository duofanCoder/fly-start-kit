package com.duofan.fly.framework.security.context.lock;


import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.enums.FlyHttpStatus;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import com.duofan.fly.core.utils.WebUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class MaliciousRequestLockoutFilter extends OncePerRequestFilter {
    private final FlyCacheService cacheService;

    public MaliciousRequestLockoutFilter(FlyCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String resourceLockCacheKey = CacheKeyUtils.getResourceLockCacheKey(request);
        if (cacheService.hasKey(resourceLockCacheKey)) {
            log.warn(LogConstant.SUSPICIOUS_OPERATION_LOG, request.getRequestURI(), WebUtils.getIp(request), "Malicious request, ip: {}");
            WebUtils.responseJson(response, FlyResult.of(FlyHttpStatus.MALICIOUS));
            return;
        }

        filterChain.doFilter(request, response);
    }

}
