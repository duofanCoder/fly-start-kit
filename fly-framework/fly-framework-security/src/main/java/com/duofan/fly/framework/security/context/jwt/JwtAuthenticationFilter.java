package com.duofan.fly.framework.security.context.jwt;

import cn.hutool.core.util.StrUtil;
import com.duofan.fly.core.constant.log.LogConstant;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.exception.FlySecurityException;
import com.duofan.fly.framework.security.exception.FlySuspiciousSecurityException;
import com.duofan.fly.framework.security.property.SecurityConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * JWT 过滤器
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/27
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter implements InitializingBean {

    private FlyTokenService tokenService;


    private AuthenticationProvider authenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(SecurityConstant.TOKEN_HEADER_KEY);
        if (StrUtil.isNotBlank(token)) {
            // 是否篡改
            if (!tokenService.verify(token)) {
                throw new FlySuspiciousSecurityException("JWT篡改");
            }
            // expired
            if (!tokenService.validate(token)) {
                throw new FlySecurityException("expired");
            }
            authenticate(token);
        }
        doFilter(request, response, filterChain);
    }


    private void authenticate(String token) {
        Map<String, Object> info = tokenService.parse(token);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(info.get("sub").toString(),
                null,
                grantedAuthorities(info.get("roles").toString()));
        this.authenticationProvider.authenticate(authenticationToken);
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

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.tokenService, "JWT管理服务未配置");
        Assert.notNull(this.authenticationProvider, "authenticationProvider未配置");
    }


    public void setTokenService(FlyTokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
}
