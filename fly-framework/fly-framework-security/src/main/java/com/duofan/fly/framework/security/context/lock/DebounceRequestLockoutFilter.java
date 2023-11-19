package com.duofan.fly.framework.security.context.lock;

import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.base.enums.web.FlyHttpStatus;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import com.duofan.fly.core.utils.WebUtils;
import com.duofan.fly.framework.security.property.SecurityConst;
import com.duofan.fly.framework.security.property.SecurityProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

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
@Slf4j
@Component
public class DebounceRequestLockoutFilter implements HandlerInterceptor {


    // 一段时间内的访问次数
    private static final long DEBOUNCE_INTERVAL = 3 * 1000; // 3 秒内
    // 设置访问次数阈值
    private static final int DEBOUNCE_REQUEST_LIMIT = 5;
    private final FlyCacheService cacheService;

    private final SecurityProperties properties;


    public DebounceRequestLockoutFilter(FlyCacheService cacheService, SecurityProperties properties) {
        this.cacheService = cacheService;
        this.properties = properties;
    }

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (ignoreUrl(request.getRequestURI())) return true;
        if (handler instanceof HandlerMethod handlerMethod) {
            FlyAccessInfo controllerAnnotation = findControllerAnnotation(handlerMethod.getBeanType());
            if (controllerAnnotation != null) {
                if (controllerAnnotation.isDebounce()) {
                    return determineKeepRequest(request, response, true);
                }
            }
        } else {
            // 处理没有映射到Controller的情况
            log.warn(LogConstant.SUSPICIOUS_OPERATION_URL_LOG, "扫描接口", WebUtils.getIp(request), request.getRequestURI(), "疑似字典爆破接口");
            determineKeepRequest(request, response, false);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Forbidden");
            return false;
        }

        return true;
    }

    private boolean ignoreUrl(String uri) {
        for (String url : properties.getPermitUrl()) {
            if (pathMatcher.match(url, uri)) {
                return true;
            }
        }
        for (String url : SecurityConst.defaultPermitUrl) {
            if (pathMatcher.match(url, uri)) {
                return true;
            }
        }
        return false;
    }

    private FlyAccessInfo findControllerAnnotation(Class<?> controllerClass) {
        return AnnotationUtils.findAnnotation(controllerClass, FlyAccessInfo.class);
    }

    protected boolean determineKeepRequest(HttpServletRequest request, HttpServletResponse response, boolean hasMapper) throws ServletException, IOException {
        String debounceKey = CacheKeyUtils.getDebounceKey(request, hasMapper); // 从方法参数中获取用户标识
        // 如果上次请求时间戳存在，并且与当前请求时间戳的间隔小于阈值，拒绝处理请求
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - DEBOUNCE_INTERVAL; // 60秒前的时间戳
        long awhileCount = cacheService.getAwhileCount(debounceKey, windowStart);
        if (awhileCount > DEBOUNCE_REQUEST_LIMIT) {
            WebUtils.responseJson(response, FlyResult.of(FlyHttpStatus.TOO_MANY_REQUESTS));
            return false;
        }

        cacheService.setCurrentTime(debounceKey, currentTime);
        return true;
    }
}
