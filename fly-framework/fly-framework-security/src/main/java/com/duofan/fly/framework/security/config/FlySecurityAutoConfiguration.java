package com.duofan.fly.framework.security.config;

import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.spi.FlyCaptchaService;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.storage.FlyRoleStorage;
import com.duofan.fly.core.storage.FlyUserStorage;
import com.duofan.fly.framework.security.constraint.FlyLoginService;
import com.duofan.fly.framework.security.constraint.FlyLoginValidRepository;
import com.duofan.fly.framework.security.constraint.FlyRegisterService;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.constraint.impl.*;
import com.duofan.fly.framework.security.context.lock.DebounceRequestLockoutFilter;
import com.duofan.fly.framework.security.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 自动配置安全相关
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Slf4j
@Configuration
public class FlySecurityAutoConfiguration {

    private final SecurityProperties properties;
    private final AuthenticationProvider authenticationProvider;
    private final FlyUserStorage userStorage;

    /**
     * 防字典组件 2023-12-30 修改默认直接注入 非实现filter接口，mvc 拦截器，
     * 不会自动进入过滤器链，需要手动添加的mvc拦截器
     *
     * @param cacheService
     * @return
     */
    @Deprecated
    public DebounceRequestLockoutFilter debounceRequestLockoutFilter(FlyCacheService cacheService) {
        log.info(LogConstant.COMPONENT_LOG, "防字典组件", "自动配置");
        return new DebounceRequestLockoutFilter(cacheService, properties);
    }

    public FlySecurityAutoConfiguration(SecurityProperties properties, AuthenticationProvider authenticationProvider, FlyUserStorage userStorage) {
        this.properties = properties;
        this.authenticationProvider = authenticationProvider;
        this.userStorage = userStorage;
    }

    @Bean("captchaLoginValidRepository")
    @ConditionalOnBean(FlyCaptchaService.class)
    @ConditionalOnProperty(name = "fly.security.login.captchaEnabled", havingValue = "true")
    FlyLoginValidRepository captchaLoginValidRepository(FlyCaptchaService captchaService, FlyCacheService cacheService) {
        log.info(LogConstant.COMPONENT_LOG, "默认登录验证", "自动配置");
        return new CaptchaLoginValidRepository(captchaService, cacheService);
    }

    @Bean
    @ConditionalOnProperty(name = "fly.security.login.errorCountEnabled", matchIfMissing = true)
    FlyLoginValidRepository errorLoginValidRepository(FlyCacheService cacheService) {
        log.info(LogConstant.COMPONENT_LOG, "默认登录密码字典拦截", "自动配置");
        return new FlyDefaultErrorCountRepository(cacheService);
    }


    @Bean("flyLoginService")
    @ConditionalOnMissingBean
    FlyLoginService flyLoginService(DelegatingLoginValidRepository loginValidRepository, FlyTokenService tokenService) {
        log.info(LogConstant.COMPONENT_LOG, "默认登陆处理", "自动配置");
        return new FlyDefaultLoginService(loginValidRepository, properties, authenticationProvider, tokenService);
    }


    @Bean("flyRegisterService")
    @ConditionalOnMissingBean
    FlyRegisterService flyRegisterService(PasswordEncoder passwordEncoder, FlyRoleStorage roleStorage) {
        log.info(LogConstant.COMPONENT_LOG, "默认注册处理", "自动配置");
        return new FlyDefaultRegisterService(userStorage, roleStorage, passwordEncoder);
    }

}
