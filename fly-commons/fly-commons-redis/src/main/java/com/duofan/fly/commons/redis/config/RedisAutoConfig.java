package com.duofan.fly.commons.redis.config;

import com.duofan.fly.commons.redis.service.RedisService;
import com.duofan.fly.core.cache.constraint.CacheService;
import com.duofan.fly.core.constant.log.LogConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.annotation.CachingConfigurer;
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

    public RedisAutoConfig(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    @ConditionalOnBean(CachingConfigurer.class)
    public CacheService cacheService(RedisTemplate<String, Object> redisTemplate) {
        log.info(LogConstant.COMPONENT_LOG, "REDIS缓存", "自动配置");
        return new RedisService(redisTemplate);
    }

}
