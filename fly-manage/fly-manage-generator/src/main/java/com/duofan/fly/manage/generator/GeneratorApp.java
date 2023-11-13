package com.duofan.fly.manage.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.duofan.fly"})
public class GeneratorApp {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApp.class, args);
    }
}
