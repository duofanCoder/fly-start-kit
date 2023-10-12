package com.duofan.fly.framework.security.config;

import com.duofan.fly.framework.security.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * json登陆配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/19
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class FlySecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
    }

    /**
     * spring security manageProvider  负责去认证
     * The ProviderManager is configured to use an AuthenticationProvider of type DaoAuthenticationProvider.
     * <a href="https://docs.spring.io/spring-security/reference/6.1-SNAPSHOT/servlet/authentication/passwords/dao-authentication-provider.html#servlet-authentication-daoauthenticationprovider">流程配置说明</a>
     *
     * @param userDetails
     * @return
     */
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetails, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetails);
        return daoAuthenticationProvider;
    }


}
