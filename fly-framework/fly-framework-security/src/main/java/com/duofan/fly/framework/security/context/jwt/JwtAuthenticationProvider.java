package com.duofan.fly.framework.security.context.jwt;

import com.duofan.fly.core.constant.log.LogConstant;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.exception.FlySuspiciousSecurityException;
import com.duofan.fly.framework.security.exception.loginValid.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.*;

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
@AllArgsConstructor
public class JwtAuthenticationProvider implements InitializingBean {

    private final FlyTokenService tokenService;
    private final UserDetailsService detailService;
    private final HandlerExceptionResolver exceptionResolver;


    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.tokenService, "JWT管理服务未配置");
    }

    private HttpServletRequest request() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    private HttpServletResponse response() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    private boolean checkToken(String token) {
        // 是否篡改
        if (!tokenService.verify(token)) {
            log.info("JWT篡改 TOKEN内容:{}", token);
            FlySuspiciousSecurityException ex = new FlySuspiciousSecurityException("JWT篡改");
            exceptionResolver.resolveException(request(), response(), null, ex);
            return false;
        }
        // expired
        if (!tokenService.validate(token)) {
            TokenExpiredException ex = new TokenExpiredException("JWT过期");
            exceptionResolver.resolveException(request(), response(), null, ex);
            return false;
        }
        return true;
    }


    /**
     * roles 角色字符串 逗号分割
     *
     * @return
     */
    private List<SimpleGrantedAuthority> grantedAuthorities(String roles) {
//        new String[]{"ANONYMOUSLY"}
        return Arrays.stream(Optional.of(roles.split(","))
                        .orElseThrow(() -> {
                            log.warn(LogConstant.SUSPICIOUS_OPERATION_LOG, "JWT认证", "角色伪造");
                            return new FlySuspiciousSecurityException("用户权限信息不存在");
                        }))
                .map(SimpleGrantedAuthority::new).toList();
    }

    public boolean authenticate(String token) throws AuthenticationException {
        if (!checkToken(token)) {
            return false;
        }
        Map<String, Object> info = tokenService.parse(token);
        // TODO use cache
        UserDetails userDetails = detailService.loadUserByUsername(info.get("sub").toString());
        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(
                userDetails,
                null,
                grantedAuthorities(info.get("roles").toString()));
        result.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(((ServletRequestAttributes)
                        Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest())
        );
        SecurityContext context = SecurityContextHolder.getContextHolderStrategy().createEmptyContext();
        context.setAuthentication(result);
        SecurityContextHolder.getContextHolderStrategy().setContext(context);
        return true;
    }
}
