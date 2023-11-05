package com.duofan.fly.commons.redis.service;

import com.duofan.fly.core.spi.cahce.FlyCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 *
 * @author geshanzsq
 * @date 2022/3/20
 */
@Slf4j
public class RedisService implements FlyCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置缓存基本对象
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存基本对象
     *
     * @param key      缓存键
     * @param value    缓存值
     * @param expire   过期时间
     * @param timeUnit 时间单位
     */
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    @Override
    public void set(String key, Object value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    /**
     * 设置缓存有效期
     *
     * @param key    缓存键
     * @param expire 过期时间
     */
    public boolean expire(String key, long expire) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, expire, TimeUnit.SECONDS));
    }

    public boolean expireAt(String key, Date expire) {
        return Boolean.TRUE.equals(redisTemplate.expireAt(key, expire));
    }

    /**
     * 设置缓存有效期
     *
     * @param key      缓存键
     * @param expire   过期时间
     * @param timeUnit 时间单位
     */
    public boolean expire(String key, long expire, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, expire, timeUnit));
    }

    /**
     * 获取缓存基本对象
     *
     * @param key 缓存键
     */
    public Object get(String key) {
        ValueOperations<String, Object> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 通配符获取缓存键
     *
     * @param pattern 通配符缓存键
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除缓存对象
     *
     * @param key 缓存键
     */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除缓存对象
     *
     * @param collection 多个对象
     */
    public Long delete(Collection<String> collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 获取缓存有效期
     *
     * @param key 缓存键
     */
    public Long getExpireTime(String key) {
        return redisTemplate.opsForValue().getOperations().getExpire(key);
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public boolean hasKeyThenDelete(String key) {
        if (this.hasKey(key)) {
            return delete(key);
        }
        return false;
    }

    /**
     * 限流
     *
     * @param key   缓存键
     * @param time  限流时间，单位秒
     * @param count 限流次数
     */
    public boolean limit(String key, int time, int count) {
        /**
         * 限流脚本
         */
        String LIMIT_SCRIPT = """
                local key = KEYS[1]
                local time = tonumber(ARGV[1])
                local count = tonumber(ARGV[2])
                local current = redis.call('get', key);
                if current and tonumber(current) > count then
                    return tonumber(current);
                end
                current = redis.call('incr', key)
                if tonumber(current) == 1 then
                    redis.call('expire', key, time)
                end
                return tonumber(current);""";
        DefaultRedisScript<Long> limitScript = new DefaultRedisScript<>(LIMIT_SCRIPT, Long.class);
        // 当前请求次数
        Long currentRequestCount = redisTemplate.execute(limitScript, Collections.singletonList(key), time, count);
        assert currentRequestCount != null;
        return currentRequestCount > count;
    }

}
