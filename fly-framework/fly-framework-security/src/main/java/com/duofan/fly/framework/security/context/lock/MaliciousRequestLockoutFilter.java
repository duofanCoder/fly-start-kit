package com.duofan.fly.framework.security.context.lock;


import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.enums.web.FlyHttpStatus;
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

/**
 * 解决问题为爆破提交、耗费成本资源的请求，
 * 改请求一般需要验证码，人机校验之类的人机验证操作。
 * <p>
 * 未认证，未通过校验直接发请求，伪装报错后锁定IP，
 * @FlyAccessResourceVerification 注解增加验证
 * <p>
 * 不能解决字典接口
 */
@Slf4j
public class MaliciousRequestLockoutFilter extends OncePerRequestFilter {
    private final FlyCacheService cacheService;

    public MaliciousRequestLockoutFilter(FlyCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String resourceLockCacheKey = CacheKeyUtils.getResourceLockCacheKey(request);
        if (cacheService.getNum(resourceLockCacheKey) > 5) {
            log.warn(LogConstant.SUSPICIOUS_OPERATION_LOG, request.getRequestURI(), WebUtils.getIp(request), "恶意请求");
            WebUtils.responseJson(response, FlyResult.of(FlyHttpStatus.MALICIOUS));
            return;
        }

        filterChain.doFilter(request, response);
    }

}
