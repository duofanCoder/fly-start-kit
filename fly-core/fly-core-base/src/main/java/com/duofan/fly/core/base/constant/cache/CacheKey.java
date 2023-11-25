package com.duofan.fly.core.base.constant.cache;

/**
 * 缓存常量
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/5
 */
public class CacheKey {
    // 验证码标识
    public static final String CAPTCHA = "captchaCode:{}:{}";
    // 校验成功后，操作码 客户端id:操作接口:校验级别
    public static final String VERIFY_OPERATION = "verifyOperation:{}:{}:{}";

    // TODO: 对应安全级别资源锁定
    // 敏感资源访问锁 IP:接口:安全级别
    public static final String RESOURCE_LOCK = "resourceLock:{}:{}:{}";
    public static final String DEBOUNCE_LOCK = "debounceLock:{}:{}";

    public static final CharSequence LOGIN_ERROR_COUNT = "loginErrorCount:{}:{}";
    public static final CharSequence LOGIN_TOKEN = "loginToken:{}:{}";
}
