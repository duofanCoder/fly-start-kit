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

import java.io.PrintWriter;

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
    private SecurityProperties securityProperties;

    @Resource
    private FlyTokenService tokenService;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
    }

    /**
     * <a href="https://www.baeldung.com/csrf-stateless-rest-api#3-credentials-stored-in-cookies" > jwt认证方式无需使用csrf防御配置 </href>
     *
     * @param http
     * @param userDetailsService
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
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
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(Customizer.withDefaults())
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.accessDeniedHandler(
                                        (request, response, accessDeniedException) -> {
                                            System.out.println("aaaa");
                                            log.error(accessDeniedException.getLocalizedMessage());
                                        }
                                )
                                .authenticationEntryPoint((request, response, authException) -> {
                                    // TODO token处理失败 在这里处理
                                    PrintWriter writer = response.getWriter();
                                    writer.println("hllo");
                                    writer.flush();
                                    writer.close();
                                })
                )
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
        filter.setAuthenticationProvider(provider);
        return filter;
    }

}
