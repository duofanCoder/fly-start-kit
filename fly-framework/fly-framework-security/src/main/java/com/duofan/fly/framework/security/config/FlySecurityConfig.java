package com.duofan.fly.framework.security.config;

import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.context.jwt.JwtAuthenticationFilter;
import com.duofan.fly.framework.security.context.jwt.JwtAuthenticationProvider;
import com.duofan.fly.framework.security.property.SecurityProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

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
@Order(value = Integer.MIN_VALUE)
public class FlySecurityConfig {

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private FlyTokenService tokenService;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/passport/**")
                            .permitAll()
                            .requestMatchers("/passport/login")
                            .permitAll()
                            .requestMatchers("/v3/api-docs/**", "/doc.html", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/favicon.ico")
                            .permitAll();
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .logout(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .build();
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

    private JwtAuthenticationFilter jwtAuthenticationFilter() {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider();
        provider.setTokenService(tokenService);
        filter.setTokenService(tokenService);
        filter.setAuthenticationProvider(provider);
        return filter;
    }

}
