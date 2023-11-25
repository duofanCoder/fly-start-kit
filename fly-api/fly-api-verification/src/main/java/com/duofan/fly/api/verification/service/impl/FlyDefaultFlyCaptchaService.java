package com.duofan.fly.api.verification.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.ICaptcha;
import com.duofan.fly.core.base.constant.security.FlyVerificationLevel;
import com.duofan.fly.core.spi.FlyCaptchaService;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 验证码
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/25
 */
@Slf4j
@Component
public class FlyDefaultFlyCaptchaService implements FlyCaptchaService {
    @Resource
    private HttpServletRequest request;

    @Resource
    private FlyCacheService cacheService;

    @Value("${fly.verification.captcha.width:120}")
    private final int WIDTH = 120;
    @Value("${fly.verification.captcha.height:40}")
    private final int HEIGHT = 40;
    @Value("${fly.verification.captcha.code-length:4}")
    private final int CODE_LENGTH = 4;
    @Value("${fly.verification.captcha.effective-time:3}")
    private final Integer effectiveTime = 3;

    @Override
    public ICaptcha createCaptcha(HttpServletRequest request) {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(WIDTH, HEIGHT, CODE_LENGTH, 4);
        String code = circleCaptcha.getCode();
        cacheService.set(CacheKeyUtils.getCaptchaCacheKey(request), code, Duration.ofMinutes(effectiveTime));
        return circleCaptcha;
    }

    @Override
    public boolean checkCaptcha(String code, HttpServletRequest request) {
        String key = CacheKeyUtils.getCaptchaCacheKey(request);
        // 检查用户输入的验证码是否与Session中的验证码匹配
        if (cacheService.hasKey(key) && code.equalsIgnoreCase(String.valueOf(cacheService.get(key)))) {
            cacheService.delete(key);
            cacheService.set(CacheKeyUtils.getVerifyCacheKey(request, true, FlyVerificationLevel.CAPTCHA), "true", Duration.ofMinutes(30));
            // 验证码匹配，返回验证成功信息
            return true;
        } else {
            cacheService.delete(key);
            // 验证码不匹配，返回验证失败信息
            return false;
        }
    }

    @Override
    public boolean checkCaptcha(String code) {
        String key = CacheKeyUtils.getCaptchaCacheKey(request);
        // 检查用户输入的验证码是否与Session中的验证码匹配
        if (cacheService.hasKey(key) && code.equalsIgnoreCase(String.valueOf(cacheService.get(key)))) {
            cacheService.delete(key);
            // 验证码匹配，返回验证成功信息
            return true;
        } else {
            cacheService.delete(key);
            // 验证码不匹配，返回验证失败信息
            return false;
        }
    }
}
