package com.duofan.fly.framework.security.context.jwt;

import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.exception.FlySuspiciousSecurityException;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import com.duofan.fly.framework.security.constraint.FlyLoginUser;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.exception.loginValid.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
public class JwtAuthenticationProvider implements InitializingBean {

    private final FlyTokenService tokenService;
    private final UserDetailsService detailService;

    private final FlyCacheService cacheService;


    public JwtAuthenticationProvider(FlyTokenService tokenService, UserDetailsService detailService, FlyCacheService cacheService) {
        this.tokenService = tokenService;
        this.detailService = detailService;
        this.cacheService = cacheService;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.tokenService, "JWT管理服务未配置");
    }

    private boolean checkToken(String token) {
        // 是否篡改
        if (!tokenService.verify(token)) {
            log.warn("JWT篡改 TOKEN内容:{}", token);
            throw new FlySuspiciousSecurityException("JWT篡改");
        }
        // expired
        if (!tokenService.validate(token)) {
            throw new TokenExpiredException("JWT过期");
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

    /**
     * 认证操作
     *
     * @param token
     * @param request
     * @throws AuthenticationException
     */
    public void authenticate(String token, HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!checkToken(token)) {
            return;
        }
        tokenService.refresh(token);
        Map<String, Object> info = tokenService.parse(token);
        // TODO use cache 、 JWT 配置化控制登录认证方式

        // 用户信息缓存在redis
        // FlyLoginUser userDetails = (FlyLoginUser) detailService.loadUserByUsername(info.get("sub").toString());
        // TODO 多角色控制
        FlyLoginUser userDetails = (FlyLoginUser) cacheService.get(CacheKeyUtils.getLoginTokenKey(info.get("sub").toString(), token));
        userDetails.setCurrentRoleNo((String) info.getOrDefault("currentRoleNo", userDetails.getRoleList().get(0).getRoleNo()));
        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(
                userDetails,
                null,
                grantedAuthorities(info.get("roles").toString()));
        // TODO 优化请求不破坏封装
        result.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContext context = SecurityContextHolder.getContextHolderStrategy().createEmptyContext();
        context.setAuthentication(result);
        SecurityContextHolder.getContextHolderStrategy().setContext(context);
    }
}
