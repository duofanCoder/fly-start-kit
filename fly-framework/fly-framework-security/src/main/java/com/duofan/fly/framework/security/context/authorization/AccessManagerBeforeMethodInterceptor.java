package com.duofan.fly.framework.security.context.authorization;

import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.core.Ordered;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.AuthorizationInterceptorsOrder;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.util.Assert;

import java.util.function.Supplier;

/**
 * 访问授权拦截器配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/7
 */
public class AccessManagerBeforeMethodInterceptor
        implements Ordered, MethodInterceptor, PointcutAdvisor, AopInfrastructureBean {

    private Supplier<Authentication> authentication = getAuthentication(
            SecurityContextHolder.getContextHolderStrategy());

    private final Log logger = LogFactory.getLog(this.getClass());

    private final Pointcut pointcut;

    private final AuthorizationManager<MethodInvocation> authorizationManager;

    private int order = AuthorizationInterceptorsOrder.FIRST.getOrder();

    private AuthorizationEventPublisher eventPublisher = AccessManagerBeforeMethodInterceptor::noPublish;

    /**
     * Creates an instance.
     *
     * @param pointcut             the {@link Pointcut} to use
     * @param authorizationManager the {@link AuthorizationManager} to use
     */
    public AccessManagerBeforeMethodInterceptor(Pointcut pointcut,
                                                AuthorizationManager<MethodInvocation> authorizationManager) {
        Assert.notNull(pointcut, "pointcut cannot be null");
        Assert.notNull(authorizationManager, "authorizationManager cannot be null");
        this.pointcut = pointcut;
        this.authorizationManager = authorizationManager;
    }

    public static AuthorizationManagerBeforeMethodInterceptor preAccess(
            AccessAnnoAuthorizationManager authorizationManager) {
        AuthorizationManagerBeforeMethodInterceptor interceptor = new AuthorizationManagerBeforeMethodInterceptor(
                new ComposablePointcut(classOrMethod()), authorizationManager);
        interceptor.setOrder(AuthorizationInterceptorsOrder.LAST.getOrder());
        return interceptor;
    }

    public static AuthorizationManagerBeforeMethodInterceptor preAccess() {
        return preAccess(new AccessAnnoAuthorizationManager());
    }

    private static Pointcut classOrMethod() {
        return new AnnotationMatchingPointcut(FlyAccessInfo.class, FlyAccessInfo.class, true);
    }

    /**
     * Determine if an {@link Authentication} has access to the {@link MethodInvocation}
     * using the configured {@link AuthorizationManager}.
     *
     * @param mi the {@link MethodInvocation} to check
     * @throws AccessDeniedException if access is not granted
     */
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        attemptAuthorization(mi);
        return mi.proceed();
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Use this {@link AuthorizationEventPublisher} to publish the
     * {@link AuthorizationManager} result.
     *
     * @param eventPublisher
     * @since 5.7
     */
    public void setAuthorizationEventPublisher(AuthorizationEventPublisher eventPublisher) {
        Assert.notNull(eventPublisher, "eventPublisher cannot be null");
        this.eventPublisher = eventPublisher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this;
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }

    /**
     * Sets the {@link SecurityContextHolderStrategy} to use. The default action is to use
     * the {@link SecurityContextHolderStrategy} stored in {@link SecurityContextHolder}.
     *
     * @since 5.8
     */
    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        this.authentication = getAuthentication(securityContextHolderStrategy);
    }

    private void attemptAuthorization(MethodInvocation mi) {
        this.logger.debug(LogMessage.of(() -> "Authorizing method invocation " + mi));
        AuthorizationDecision decision = this.authorizationManager.check(this.authentication, mi);
        this.eventPublisher.publishAuthorizationEvent(this.authentication, mi, decision);
        if (decision != null && !decision.isGranted()) {
            this.logger.debug(LogMessage.of(() -> "Failed to authorize " + mi + " with authorization manager "
                    + this.authorizationManager + " and decision " + decision));
            throw new AccessDeniedException("Access Denied");
        }
        this.logger.debug(LogMessage.of(() -> "Authorized method invocation " + mi));
    }

    private Supplier<Authentication> getAuthentication(SecurityContextHolderStrategy strategy) {
        return () -> {
            Authentication authentication = strategy.getContext().getAuthentication();
            if (authentication == null) {
                throw new AuthenticationCredentialsNotFoundException(
                        "An Authentication object was not found in the SecurityContext");
            }
            return authentication;
        };
    }

    private static <T> void noPublish(Supplier<Authentication> authentication, T object,
                                      AuthorizationDecision decision) {

    }
}
