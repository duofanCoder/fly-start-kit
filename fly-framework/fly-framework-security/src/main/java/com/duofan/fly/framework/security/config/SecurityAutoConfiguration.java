package com.duofan.fly.framework.security.config;

import com.duofan.fly.framework.security.constraint.FlyLoginValidRepository;
import com.duofan.fly.framework.security.constraint.impl.CaptchaLoginValidRepository;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;


/**
 * 自动配置安全相关
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@AutoConfiguration
public class SecurityAutoConfiguration {
    @Bean("captchaLoginValidRepository")
    @ConditionalOnProperty(prefix = "fly.security.login", name = "captchaEnabled", matchIfMissing = true)
    FlyLoginValidRepository captchaLoginValidRepository() {
        return new CaptchaLoginValidRepository();
    }
}
