package com.duofan.fly.web.starter;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = {"com.duofan.fly"})
public class FlyStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlyStarterApplication.class, args);
    }

}
