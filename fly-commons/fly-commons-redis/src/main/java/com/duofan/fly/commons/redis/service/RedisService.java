package com.duofan.fly.commons.redis.service;

import cn.hutool.core.util.ObjUtil;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 *
 * @author geshanzsq
 * @date 2022/3/20
 */
@Slf4j
public class RedisService implements FlyCacheService {


    private final String projectName;

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate, String projectName) {
        this.projectName = projectName;
        this.redisTemplate = redisTemplate;
    }

    private String getRealKey(String key) {
        return projectName + ":" + key;
    }

    /**
     * 设置缓存基本对象
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(getRealKey(key), value);
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
        redisTemplate.opsForValue().set(getRealKey(key), value, expire, timeUnit);
    }

    @Override
    public void set(String key, Object value, Duration duration) {
        redisTemplate.opsForValue().set(getRealKey(key), value, duration);
    }

    @Override
    public void removeBefore(String key, long windowStart) {
        redisTemplate.opsForZSet().removeRangeByScore(getRealKey(key), 0, windowStart);
    }

    // 获取某一时间内的记录
    @Override
    public long getAwhileCount(String key, long windowStart) {
        // 移除60秒前的请求记录
        redisTemplate.opsForZSet().removeRangeByScore(getRealKey(key), 0, windowStart);

        // 获取60秒内的请求次数
        return Optional.ofNullable(redisTemplate.opsForZSet().zCard(getRealKey(key))).orElse(0L);
    }

    @Override
    public void setCurrentTime(String key, long currentTime) {
        redisTemplate.opsForZSet().add(getRealKey(key), String.valueOf(currentTime), currentTime);
    }


    @Override

    public long increment(String key, long delta, long initValue, Duration duration) {
        if (this.hasKey(key)) {
            // 自增 或者 设置初始值
            return redisTemplate.opsForValue().increment(getRealKey(key), delta);
        } else {
            // 初始化设置默认值和失效时间
            this.set(key, initValue, duration);
            return initValue;
        }
    }

    /**
     * 设置缓存有效期
     *
     * @param key    缓存键
     * @param expire 过期时间
     */
    public boolean expire(String key, long expire) {
        return Boolean.TRUE.equals(redisTemplate.expire(getRealKey(key), expire, TimeUnit.SECONDS));
    }

    @Override
    public boolean expire(String key, Duration expire) {
        if (this.hasKey(key)) {
            return Boolean.TRUE.equals(redisTemplate.expire(getRealKey(key), expire));
        }
        return false;
    }

    public boolean expireAt(String key, Date expire) {
        return Boolean.TRUE.equals(redisTemplate.expireAt(getRealKey(key), expire));
    }

    /**
     * 设置缓存有效期
     *
     * @param key      缓存键
     * @param expire   过期时间
     * @param timeUnit 时间单位
     */
    public boolean expire(String key, long expire, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.expire(getRealKey(key), expire, timeUnit));
    }

    /**
     * 获取缓存基本对象
     *
     * @param key 缓存键
     */
    public Object get(String key) {
        ValueOperations<String, Object> operation = redisTemplate.opsForValue();
        return operation.get(getRealKey(key));
    }

    @Override
    public long getNum(String key) {
        ValueOperations<String, Object> operation = redisTemplate.opsForValue();
        String num = String.valueOf(ObjUtil.defaultIfNull(operation.get(getRealKey(key)), "0"));
        return Long.parseLong(num);
    }

    /**
     * 通配符获取缓存键
     *
     * @param pattern 通配符缓存键
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(getRealKey(pattern));
    }

    /**
     * 删除缓存对象
     *
     * @param key 缓存键
     */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(getRealKey(key)));
    }

    /**
     * 删除缓存对象
     *
     * @param collection 多个对象
     */
    public Long delete(Collection<String> collection) {
        return redisTemplate.delete(collection.stream().map(this::getRealKey).toList());
    }

    /**
     * 获取缓存有效期
     *
     * @param key 缓存键
     */
    public Long getExpireTime(String key) {
        return redisTemplate.opsForValue().getOperations().getExpire(getRealKey(key));
    }

    @Override
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(getRealKey(key)));
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
