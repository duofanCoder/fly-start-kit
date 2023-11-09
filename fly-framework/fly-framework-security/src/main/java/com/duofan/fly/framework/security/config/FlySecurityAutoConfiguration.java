package com.duofan.fly.framework.security.config;

import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.storage.FlyRoleStorage;
import com.duofan.fly.core.storage.FlyUserStorage;
import com.duofan.fly.framework.security.constraint.FlyLoginService;
import com.duofan.fly.framework.security.constraint.FlyLoginValidRepository;
import com.duofan.fly.framework.security.constraint.FlyRegisterService;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.constraint.impl.CaptchaLoginValidRepository;
import com.duofan.fly.framework.security.constraint.impl.DelegatingLoginValidRepository;
import com.duofan.fly.framework.security.constraint.impl.FlyDefaultLoginService;
import com.duofan.fly.framework.security.constraint.impl.FlyDefaultRegisterService;
import com.duofan.fly.framework.security.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
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


    public FlySecurityAutoConfiguration(SecurityProperties properties, AuthenticationProvider authenticationProvider, FlyUserStorage userStorage) {
        this.properties = properties;
        this.authenticationProvider = authenticationProvider;
        this.userStorage = userStorage;
    }

    @Bean("captchaLoginValidRepository")
    @ConditionalOnProperty(name = "fly.security.login.captchaEnabled", havingValue = "true")
    FlyLoginValidRepository captchaLoginValidRepository() {
        log.info(LogConstant.COMPONENT_LOG, "默认登陆验证码", "自动配置");
        return new CaptchaLoginValidRepository();
    }

    @Bean("flyLoginService")
    @ConditionalOnMissingBean
    FlyLoginService flyLoginService(DelegatingLoginValidRepository loginValidRepository, FlyTokenService tokenService) {
        log.info(LogConstant.COMPONENT_LOG, "默认登陆组件", "自动配置");
        return new FlyDefaultLoginService(loginValidRepository, properties, authenticationProvider, tokenService);
    }


    @Bean("flyRegisterService")
    @ConditionalOnMissingBean
    FlyRegisterService flyRegisterService(PasswordEncoder passwordEncoder, FlyRoleStorage roleStorage) {
        log.info(LogConstant.COMPONENT_LOG, "默认注册组件", "自动配置");
        return new FlyDefaultRegisterService(userStorage, roleStorage, passwordEncoder);
    }

}
