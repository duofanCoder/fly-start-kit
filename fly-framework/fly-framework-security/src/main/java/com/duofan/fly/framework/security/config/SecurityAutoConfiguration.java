package com.duofan.fly.framework.security.config;

import com.duofan.fly.core.constant.log.LogConstant;
import com.duofan.fly.framework.security.constraint.FlyLoginService;
import com.duofan.fly.framework.security.constraint.FlyLoginValidRepository;
import com.duofan.fly.framework.security.constraint.impl.CaptchaLoginValidRepository;
import com.duofan.fly.framework.security.constraint.impl.DelegatingLoginValidRepository;
import com.duofan.fly.framework.security.constraint.impl.FlyDefaultLoginService;
import com.duofan.fly.framework.security.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;


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
public class SecurityAutoConfiguration {
    @Bean("captchaLoginValidRepository")
    @ConditionalOnProperty(prefix = "fly.security.login", name = "captchaEnabled", matchIfMissing = true)
    FlyLoginValidRepository captchaLoginValidRepository() {
        log.info(LogConstant.COMPONENT_LOG, "默认登陆验证码", "自动配置");
        return new CaptchaLoginValidRepository();
    }

    //    @Bean("flyLoginService")
//    @ConditionalOnProperty(prefix = "fly.security.login", name = "captchaEnabled", matchIfMissing = true)
//    @ConditionalOnBean
    FlyLoginService flyLoginService(DelegatingLoginValidRepository loginValidRepository, SecurityProperties properties, AuthenticationProvider authenticationProvider) {
        log.info(LogConstant.COMPONENT_LOG, "默认登陆组件", "自动配置");
        return new FlyDefaultLoginService(loginValidRepository, properties, authenticationProvider);
    }
}
