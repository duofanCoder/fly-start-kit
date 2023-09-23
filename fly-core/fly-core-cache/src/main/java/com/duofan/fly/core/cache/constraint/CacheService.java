package com.duofan.fly.core.cache.constraint;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 抽象缓存服务
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/23
 */
public interface CacheService {


    /**
     * 设置缓存基本对象
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    void set(String key, Object value);

    /**
     * 设置缓存基本对象
     *
     * @param key      缓存键
     * @param value    缓存值
     * @param expire   过期时间
     * @param timeUnit 时间单位
     */
    void set(String key, Object value, long expire, TimeUnit timeUnit);

    /**
     * 设置缓存有效期
     *
     * @param key    缓存键
     * @param expire 过期时间
     */
    boolean expire(String key, long expire);

    /**
     * 设置缓存有效期
     *
     * @param key      缓存键
     * @param expire   过期时间
     * @param timeUnit 时间单位
     */
    boolean expire(String key, long expire, TimeUnit timeUnit);

    /**
     * 获取缓存基本对象
     *
     * @param key 缓存键
     */
    Object get(String key);

    /**
     * 通配符获取缓存键
     *
     * @param pattern 通配符缓存键
     */
    Collection<String> keys(String pattern);

    /**
     * 删除缓存对象
     *
     * @param key 缓存键
     */
    boolean delete(String key);

    /**
     * 删除缓存对象
     *
     * @param collection 多个对象
     */
    Long delete(Collection<String> collection);

    /**
     * 获取缓存有效期
     *
     * @param key 缓存键
     */
    Long getExpireTime(String key);


}
