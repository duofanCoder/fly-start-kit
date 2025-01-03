package com.duofan.fly.commons.redis.config;

import com.duofan.fly.commons.redis.service.RedisService;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 自动配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/23
 */
@Slf4j
@Configuration
public class RedisAutoConfig {
    private final RedisTemplate<String, Object> redisTemplate;

    private final String projectName;

    public RedisAutoConfig(RedisTemplate<String, Object> redisTemplate, @Value("${spring.application.name}") String projectName) {
        this.redisTemplate = redisTemplate;
        this.projectName = projectName;
    }

    @Bean
    @ConditionalOnMissingBean(FlyCacheService.class)
    public FlyCacheService cacheService(RedisTemplate<String, Object> redisTemplate) {
        log.info(LogConstant.COMPONENT_LOG, "REDIS缓存", "自动配置");
        return new RedisService(redisTemplate, projectName);
    }

}
