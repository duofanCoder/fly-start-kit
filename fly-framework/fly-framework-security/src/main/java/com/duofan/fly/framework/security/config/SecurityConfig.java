package com.duofan.fly.framework.security.config;

import cn.hutool.core.collection.CollUtil;
import com.duofan.fly.framework.security.property.SecurityProperties;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {
    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(securityProperties.getNoAuthUrls()).permitAll()
                                .anyRequest().authenticated()
                );

        return http.build();
    }
    @Configuration
    public static class FormLoginSecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests((authorizeRequests) ->
                            authorizeRequests
                                    .requestMatchers("/authentication/**").permitAll()
                    )
                    .formLogin((formLogin) ->
                            formLogin
                                    .usernameParameter("username")
                                    .passwordParameter("password")
                                    .loginPage("/authentication/login")
                                    .failureUrl("/authentication/login?failed")
                                    .loginProcessingUrl("/authentication/login/process")
                    );
            return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            UserDetails user = User.withDefaultPasswordEncoder()
                    .username("user")
                    .password("password")
                    .roles("USER")
                    .build();
            return new InMemoryUserDetailsManager(user);
        }
    }

}
