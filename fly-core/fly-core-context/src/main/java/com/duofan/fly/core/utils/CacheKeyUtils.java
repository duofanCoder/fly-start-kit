package com.duofan.fly.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.duofan.fly.core.base.constant.cache.CacheKey;
import com.duofan.fly.core.base.constant.security.FlyVerificationLevel;
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

    /**
     * 缓存校验成功拼争
     *
     * @param request
     * @param isSuccessVerifyToken 是否是校验成功设置缓存
     * @param level                校验级别
     * @return
     */
    public static String getVerifyCacheKey(HttpServletRequest request, boolean isSuccessVerifyToken, FlyVerificationLevel level) {
        if (level == null) {
            throw new IllegalArgumentException("校验级别不能为空");
        }
        String nextId = null;
        String levelCode = level.getCode();
        if (isSuccessVerifyToken) {
            nextId = StrUtil.nullToDefault(request.getParameter("nextId"), WebUtils.getIp(request));
        } else {
            nextId = DigestUtil.sha256Hex(request.getRequestURI());
        }

        return StrUtil.format(CacheKey.VERIFY_OPERATION, request.getSession().getId(), nextId, levelCode);
    }


    /**
     * 资源访问锁
     *
     * @param request * @param level   被锁资源安全级别
     * @return
     */
    public static String getResourceLockCacheKey(HttpServletRequest request) {
        return StrUtil.format(CacheKey.RESOURCE_LOCK, WebUtils.getIp(request));
    }

    /**
     * 防抖、防字典爆破
     *
     * @param request
     * @param hasMapper
     * @return
     */
    public static String getDebounceKey(HttpServletRequest request, boolean hasMapper) {
        if (hasMapper) {
            return StrUtil.format(CacheKey.DEBOUNCE_LOCK, WebUtils.getIp(request), request.getRequestURI());
        }
        return StrUtil.format(CacheKey.DEBOUNCE_LOCK, WebUtils.getIp(request), "no-mapper-uri");
    }


    // 登录出错次数验证key
    public static String getLoginErrorCountKey(String ip, String username) {
        return StrUtil.format(CacheKey.LOGIN_ERROR_COUNT, ip, username);
    }

    public static String getLoginTokenKey(String username, String token) {
        return StrUtil.format(CacheKey.LOGIN_TOKEN, username, token);
    }
}
