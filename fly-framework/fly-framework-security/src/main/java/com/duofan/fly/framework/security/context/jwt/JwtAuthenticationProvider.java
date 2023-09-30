package com.duofan.fly.framework.security.context.jwt;

import com.duofan.fly.core.constant.log.LogConstant;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.exception.FlySecurityException;
import com.duofan.fly.framework.security.exception.FlySuspiciousSecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public void setTokenService(FlyTokenService tokenService) {
        this.tokenService = tokenService;
    }

    private FlyTokenService tokenService;


    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.tokenService, "JWT管理服务未配置");
    }

    private void checkToken(String token) {
        // 是否篡改
        if (!tokenService.verify(token)) {
            log.info("JWT篡改 TOKEN内容:{}", token);
            throw new FlySuspiciousSecurityException("JWT篡改");
        }
        // expired
        if (!tokenService.validate(token)) {
            throw new FlySecurityException("JWT过期");
        }
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

    public void authenticate(String token) throws AuthenticationException {
        checkToken(token);
        Map<String, Object> info = tokenService.parse(token);
        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(info.get("sub").toString(),
                null,
                grantedAuthorities(info.get("roles").toString()));
        // TODO 添加上下文用户信息 loadUserDetails 到detail 里
        SecurityContext context = SecurityContextHolder.getContextHolderStrategy().createEmptyContext();
        context.setAuthentication(result);
        SecurityContextHolder.getContextHolderStrategy().setContext(context);
    }
}
