package com.duofan.fly.framework.security.constraint.impl;

import com.duofan.fly.framework.security.constraint.FlyLogoutService;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.context.logout.TokenLogoutHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessEventPublishingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/1
 */
@Component
public class FlyDefaultLogoutService implements FlyLogoutService {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    private final LogoutHandler handler;

    private final FlyTokenService tokenService;

    public FlyDefaultLogoutService(FlyTokenService tokenService) {
        this.tokenService = tokenService;
        this.handler = new CompositeLogoutHandler(
                new SecurityContextLogoutHandler(),
                new TokenLogoutHandler(tokenService),
                new LogoutSuccessEventPublishingLogoutHandler()
        );
    }


    @Override
    public void logout() {
        Authentication auth = securityContextHolderStrategy.getContext().getAuthentication();
        handler.logout(request(), response(), auth);
    }

    private HttpServletRequest request() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    private HttpServletResponse response() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }
}
