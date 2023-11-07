package com.duofan.fly.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.duofan.fly.core.base.constant.cache.CacheKey;
import com.duofan.fly.core.base.constant.log.LogConstant;
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
     * 获取校验整个凭证
     *
     * @param request
     * @param isSuccessVerifyToken 是否是校验成功设置缓存
     * @return
     */
    public static String getVerifyCacheKey(HttpServletRequest request, boolean isSuccessVerifyToken) {
        String nextId = null;
        if (isSuccessVerifyToken) {
            nextId = StrUtil.nullToDefault(request.getParameter("nextId"), WebUtils.getIp(request));
            log.info(LogConstant.SUSPICIOUS_OPERATION_LOG, "校验码验证", WebUtils.getIp(request), "验证成功，非法请求");
        } else {
            nextId = DigestUtil.sha256Hex(request.getRequestURI());
            log.info("nextId:{}", nextId);
        }

        return StrUtil.format(CacheKey.CAPTCHA, request.getSession().getId(), nextId);
    }


    // 敏感资源访问锁缓存key
    public static String getResourceLockCacheKey(HttpServletRequest request) {
        return StrUtil.format(CacheKey.RESOURCE_LOCK, WebUtils.getIp(request));
    }
}
