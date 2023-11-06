package com.duofan.fly.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.duofan.fly.core.base.constant.cache.CacheKey;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 验证相关工具
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/5
 */
@Slf4j
public class CacheKeyUtils {

    public static String getCaptchaCacheKey(HttpServletRequest request) {
        return StrUtil.format(CacheKey.CAPTCHA, request.getSession().getId());
    }

    // 获取验证成功后操作码缓存key
    public static String getVerifyCacheKey(HttpServletRequest request) {
        String nextId = DigestUtil.sha256Hex(request.getRequestURI());
        log.info("nextId:{}", nextId);
        return StrUtil.format(CacheKey.CAPTCHA, request.getSession().getId(), nextId);
    }


    // 敏感资源访问锁缓存key
    public static String getResourceLockCacheKey(HttpServletRequest request, String nextId) {
        return StrUtil.format(CacheKey.RESOURCE_LOCK, request.getSession().getId(), nextId);
    }
}
