package com.duofan.fly.core.config;

import com.duofan.fly.core.AuthenticationEndpointAnalysis;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlyFrameworkConfig {
    
    @Bean
    public AuthenticationEndpointAnalysis analysis(ApplicationContext applicationContext) {
        return new AuthenticationEndpointAnalysis(applicationContext);
    }
}
