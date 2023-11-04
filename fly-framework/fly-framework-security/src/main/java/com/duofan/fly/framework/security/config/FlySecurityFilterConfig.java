package com.duofan.fly.framework.security.config;

import com.duofan.fly.core.AuthenticationEndpointAnalysis;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.enums.FlyHttpStatus;
import com.duofan.fly.core.domain.FlyApi;
import com.duofan.fly.core.utils.WebUtils;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.context.jwt.JwtAuthenticationFilter;
import com.duofan.fly.framework.security.context.jwt.JwtAuthenticationProvider;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

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
    @Resource(name = "handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;
    @Resource
    private AuthenticationEndpointAnalysis analysis;
    @Resource
    private FlyTokenService tokenService;


    /**
     * <a href="https://www.baeldung.com/csrf-stateless-rest-api#3-credentials-stored-in-cookies" > jwt认证方式无需使用csrf防御配置 </href>
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetails) throws Exception {


        return http
                // 跨站攻击关闭
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/v3/api-docs/**", "/error/**", "/doc.html", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/favicon.ico")
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
                                {
                                    WebUtils.responseJson(response, FlyResult.of(FlyHttpStatus.FORBIDDEN));
                                })
                                .authenticationEntryPoint((request, response, authException) ->
                                {
                                    WebUtils.responseJson(response, FlyResult.of(FlyHttpStatus.FORBIDDEN));
                                }))
                .build();
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter(UserDetailsService userDetails) {
        return new JwtAuthenticationFilter(new JwtAuthenticationProvider(tokenService, userDetails));
    }
}
