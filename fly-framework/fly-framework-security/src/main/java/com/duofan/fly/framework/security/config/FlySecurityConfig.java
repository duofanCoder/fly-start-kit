package com.duofan.fly.framework.security.config;

import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.context.authorization.AccessAnnoAuthorizationManager;
import com.duofan.fly.framework.security.context.jwt.JwtAuthenticationFilter;
import com.duofan.fly.framework.security.context.jwt.JwtAuthenticationProvider;
import com.duofan.fly.framework.security.property.SecurityProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

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

    @Resource
    private FlyTokenService tokenService;

    @Resource(name = "handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Resource(type = AccessAnnoAuthorizationManager.class)
    private AccessAnnoAuthorizationManager authorizationManager;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
    }

    /**
     * <a href="https://www.baeldung.com/csrf-stateless-rest-api#3-credentials-stored-in-cookies" > jwt认证方式无需使用csrf防御配置 </href>
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetails, AccessAnnoAuthorizationManager flyAuthorizationManager) throws Exception {
        return http
                // 跨站攻击关闭
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/v3/api-docs/**", "/doc.html", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/favicon.ico")
                        .permitAll()
                        .requestMatchers("/api/v1/passport/**")
                        .permitAll()
                        .requestMatchers("/api/v1/**")
                        .authenticated()
                        .anyRequest()
                        .denyAll()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetails)
                .addFilterBefore(jwtAuthenticationFilter(userDetails, exceptionResolver), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer
                                .accessDeniedHandler((request, response, accessDeniedException) -> response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()))
                                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase()))
                )
                .build();

//        HttpHeaders.AUTHORIZATION
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

    private JwtAuthenticationFilter jwtAuthenticationFilter(UserDetailsService userDetails, HandlerExceptionResolver exceptionResolver) {
        return new JwtAuthenticationFilter(new JwtAuthenticationProvider(tokenService, userDetails, exceptionResolver));
    }


}
