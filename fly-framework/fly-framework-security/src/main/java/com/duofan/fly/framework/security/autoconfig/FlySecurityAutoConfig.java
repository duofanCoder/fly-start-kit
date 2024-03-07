package com.duofan.fly.framework.security.autoconfig;

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
import com.duofan.fly.framework.security.context.jwt.JwtAuthenticationFilter;
import com.duofan.fly.framework.security.context.jwt.JwtAuthenticationProvider;
import com.duofan.fly.framework.security.context.lock.DebounceRequestLockoutFilter;
import com.duofan.fly.framework.security.context.lock.MaliciousRequestLockoutFilter;
import com.duofan.fly.framework.security.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
public class FlySecurityAutoConfig {

    @Bean
    @ConditionalOnBean(FlyCacheService.class)
    // 配置项为false或者不配置都不会生效
    @ConditionalOnProperty(name = "fly.security.filter.malicious-request-lockout.enabled", matchIfMissing = true)
    MaliciousRequestLockoutFilter maliciousRequestLockoutFilter(FlyCacheService cacheService) {
        return new MaliciousRequestLockoutFilter(cacheService);
    }


    @Bean
    @ConditionalOnBean({FlyCacheService.class, FlyTokenService.class})
    // 配置项为false或者不配置都不会生效
    @ConditionalOnProperty(name = "fly.security.login.authentication-method", havingValue = "jwt", matchIfMissing = false)
    JwtAuthenticationFilter jwtAuthenticationFilter(JwtAuthenticationProvider jwtAuthenticationProvider) {
        return new JwtAuthenticationFilter(jwtAuthenticationProvider);
    }
    @Bean
    @ConditionalOnBean({FlyCacheService.class, FlyTokenService.class,UserDetailsService.class})
    // 配置项为false或者不配置都不会生效
    @ConditionalOnProperty(name = "fly.security.login.authentication-method", havingValue = "jwt")
    JwtAuthenticationProvider jwtAuthenticationProvider(UserDetailsService userDetails,
                                                    FlyTokenService tokenService,
                                                    FlyCacheService cacheService) {
        return new JwtAuthenticationProvider(tokenService, userDetails, cacheService);
    }

    @Bean("captchaLoginValidRepository")
    @ConditionalOnBean({FlyCaptchaService.class, FlyCacheService.class})
    @ConditionalOnProperty(name = "fly.security.login.captchaEnabled", havingValue = "true")
    FlyLoginValidRepository captchaLoginValidRepository(FlyCaptchaService captchaService, FlyCacheService cacheService) {
        log.info(LogConstant.COMPONENT_LOG, "默认登录验证", "自动配置");
        return new CaptchaLoginValidRepository(captchaService, cacheService);
    }

    @Bean
    @ConditionalOnBean(FlyCacheService.class)
    @ConditionalOnProperty(name = "fly.security.login.errorCountEnabled", matchIfMissing = true)
    FlyLoginValidRepository errorLoginValidRepository(FlyCacheService cacheService) {
        log.info(LogConstant.COMPONENT_LOG, "默认登录密码字典拦截", "自动配置");
        return new FlyDefaultErrorCountRepository(cacheService);
    }


    @Bean("flyLoginService")
    @ConditionalOnMissingBean
    @ConditionalOnBean({FlyLoginValidRepository.class, FlyTokenService.class})
    FlyLoginService flyLoginService(DelegatingLoginValidRepository loginValidRepository, FlyTokenService tokenService,
                                    SecurityProperties properties, AuthenticationProvider authenticationProvider
                                    ) {
        log.info(LogConstant.COMPONENT_LOG, "默认登陆处理", "自动配置");
        return new FlyDefaultLoginService(loginValidRepository, properties, authenticationProvider, tokenService);
    }


    @Bean("flyRegisterService")
    @ConditionalOnMissingBean
    FlyRegisterService flyRegisterService(FlyUserStorage userStorage,PasswordEncoder passwordEncoder, FlyRoleStorage roleStorage) {
        log.info(LogConstant.COMPONENT_LOG, "默认注册处理", "自动配置");
        return new FlyDefaultRegisterService(userStorage, roleStorage, passwordEncoder);
    }

    /**
     * 防字典组件 2023-12-30 修改默认直接注入 非实现filter接口，mvc 拦截器，
     * 不会自动进入过滤器链，需要手动添加的mvc拦截器
     *
     * @param cacheService
     * @return
     */
    @Deprecated
    public DebounceRequestLockoutFilter debounceRequestLockoutFilter(FlyCacheService cacheService, SecurityProperties properties) {
        log.info(LogConstant.COMPONENT_LOG, "防字典组件", "自动配置");
        return new DebounceRequestLockoutFilter(cacheService, properties);
    }

}
