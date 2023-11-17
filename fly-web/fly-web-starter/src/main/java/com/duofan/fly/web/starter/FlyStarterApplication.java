package com.duofan.fly.web.starter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Slf4j
@EntityScan("com.duofan.fly.web.starter.entity")
@SpringBootApplication(scanBasePackages = {"com.duofan.fly"})
public class FlyStarterApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(FlyStarterApplication.class, args);
    }

}
