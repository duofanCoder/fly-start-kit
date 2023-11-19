package com.duofan.fly.framework.security.config;

import cn.hutool.core.collection.CollUtil;
import com.duofan.fly.core.AuthenticationEndpointAnalysis;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.enums.web.FlyHttpStatus;
import com.duofan.fly.core.domain.FlyApi;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.WebUtils;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.context.jwt.JwtAuthenticationFilter;
import com.duofan.fly.framework.security.context.jwt.JwtAuthenticationProvider;
import com.duofan.fly.framework.security.context.lock.MaliciousRequestLockoutFilter;
import com.duofan.fly.framework.security.property.SecurityConst;
import com.duofan.fly.framework.security.property.SecurityProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 拦截器配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/12
 */
@Slf4j
@Configuration
public class FlySecurityFilterConfig {
    @Resource
    private AuthenticationEndpointAnalysis analysis;
    @Resource
    private FlyTokenService tokenService;

    @Resource
    private FlyCacheService cacheService;

    @Value("${fly.security.filter.malicious-request-lockout.enabled:true}")
    private boolean maliciousRequestLockoutFilterEnabled = true;

    @Resource
    private SecurityProperties properties;

    /**
     * <a href="https://www.baeldung.com/csrf-stateless-rest-api#3-credentials-stored-in-cookies" > jwt认证方式无需使用csrf防御配置 </href>
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetails) throws Exception {
        // 跨站攻击关闭
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
        if (maliciousRequestLockoutFilterEnabled) {
            http.addFilterBefore(maliciousRequestLockoutFilter(), UsernamePasswordAuthenticationFilter.class);
        }
        if (CollUtil.isNotEmpty(properties.getPermitUrl())) {
            http.authorizeHttpRequests(req ->
                    req.requestMatchers(properties.getPermitUrl().toArray(new String[1]))
                            .permitAll());
        }

        return http
                .authorizeHttpRequests(req -> req
                        .requestMatchers(SecurityConst.defaultPermitUrl.toArray(new String[1]))
                        .permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers(analysis.getWhiteApis().stream().map(FlyApi::getRequestUrl).toList().toArray(new String[1]))
                        .permitAll()
                        .requestMatchers("/api/**")
                        .authenticated()
                        .anyRequest()
                        .denyAll()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetails)
                .addFilterBefore(jwtAuthenticationFilter(userDetails), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer
                                .accessDeniedHandler((request, response, accessDeniedException) ->
                                        WebUtils.responseJson(response, FlyResult.of(FlyHttpStatus.FORBIDDEN)))
                                .authenticationEntryPoint((request, response, authException) ->
                                        WebUtils.responseJson(response, FlyResult.of(FlyHttpStatus.FORBIDDEN))))
                .build();
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter(UserDetailsService userDetails) {
        return new JwtAuthenticationFilter(new JwtAuthenticationProvider(tokenService, userDetails));
    }

    @Bean
    // 配置项为false或者不配置都不会生效
    @ConditionalOnProperty(name = "fly.security.filter.malicious-request-lockout.enabled", matchIfMissing = true)
    MaliciousRequestLockoutFilter maliciousRequestLockoutFilter() {
        return new MaliciousRequestLockoutFilter(cacheService);
    }
}
