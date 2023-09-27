package com.duofan.fly.framework.security.context.jwt;

import com.duofan.fly.framework.security.constraint.FlyTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * jwt认证校验其
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/27
 */
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider, InitializingBean {

    public void setTokenService(FlyTokenService tokenService) {
        this.tokenService = tokenService;
    }

    private FlyTokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(authentication.getPrincipal(),
                authentication.getCredentials(), authentication.getAuthorities());
        // TODO 添加上下文用户信息到detail 里
        result.setDetails(authentication.getDetails());
        SecurityContext context = SecurityContextHolder.getContextHolderStrategy().createEmptyContext();
        context.setAuthentication(result);
        SecurityContextHolder.getContextHolderStrategy().setContext(context);
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.tokenService, "JWT管理服务未配置");
    }
}
