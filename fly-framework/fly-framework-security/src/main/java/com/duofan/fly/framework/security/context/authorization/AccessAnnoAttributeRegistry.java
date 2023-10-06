package com.duofan.fly.framework.security.context.authorization;

import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodClassKey;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/6
 */
public class AccessAnnoAttributeRegistry {
    private final Map<MethodClassKey, FlyResourceInfo> cachedAttributes = new ConcurrentHashMap<>();

    FlyResourceInfo resolveAttribute(Method method, Class<?> targetClass) {
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
        FlyAccessInfo preAuthorize = findPreAuthorizeAnnotation(specificMethod);
        if (preAuthorize == null) {
            return null;
        }
        return new FlyResourceInfo(preAuthorize);
    }

    final FlyResourceInfo getAttribute(MethodInvocation mi) {
        Method method = mi.getMethod();
        Object target = mi.getThis();
        Class<?> targetClass = (target != null) ? target.getClass() : null;
        MethodClassKey cacheKey = new MethodClassKey(method, targetClass);
        return this.cachedAttributes.computeIfAbsent(cacheKey, (k) -> resolveAttribute(method, targetClass));
    }

    private FlyAccessInfo findPreAuthorizeAnnotation(Method method) {
        FlyAccessInfo preAuthorize = AnnotationUtils.findAnnotation(method, FlyAccessInfo.class);
        return (preAuthorize != null) ? preAuthorize
                : AnnotationUtils.findAnnotation(method.getDeclaringClass(), FlyAccessInfo.class);
    }
}
