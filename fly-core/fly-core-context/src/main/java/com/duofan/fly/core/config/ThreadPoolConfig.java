package com.duofan.fly.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 核心线程数
        executor.setMaxPoolSize(20);  // 最大线程数
        executor.setQueueCapacity(30); // 等待队列容量
        executor.setThreadNamePrefix("FlyCommonThreadPool-"); // 线程名前缀
        executor.initialize(); // 初始化线程池
        return executor;
    }
}