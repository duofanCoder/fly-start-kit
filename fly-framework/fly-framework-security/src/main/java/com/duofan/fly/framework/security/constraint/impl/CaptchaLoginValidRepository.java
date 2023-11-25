package com.duofan.fly.framework.security.constraint.impl;

import cn.hutool.json.JSONUtil;
import com.duofan.fly.core.spi.FlyCaptchaService;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.framework.security.constraint.FlyLoginValidRepository;
import com.duofan.fly.framework.security.exception.LoginValidException;
import com.duofan.fly.framework.security.exception.loginValid.FlyCaptchaValidException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 验证码校验器
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/2
 */
@Slf4j
public class CaptchaLoginValidRepository implements FlyLoginValidRepository {

    private final String captchaParam = "captcha";

    private final FlyCaptchaService captchaService;

    private final FlyCacheService cacheService;

    public CaptchaLoginValidRepository(FlyCaptchaService captchaService, FlyCacheService cacheService) {
        this.captchaService = captchaService;
        this.cacheService = cacheService;
    }


    @Override
    public void doCheck(Map<String, Object> data) throws LoginValidException {
        String code = data.getOrDefault(captchaParam, "").toString();
        if (code.isBlank()) {
            throw new FlyCaptchaValidException("验证码不能为空");
        }

        boolean isSuccess = captchaService.checkCaptcha(code);
        if (!isSuccess) {
            throw new FlyCaptchaValidException("验证码错误");
        }
        log.info("校验验证码成功：{}", JSONUtil.toJsonStr(data));
    }

    @Override
    public boolean supportError(LoginValidException e) {
        return e instanceof FlyCaptchaValidException;
    }

    @Override
    public void errorHandle(Map<String, Object> data, LoginValidException e) {
        log.warn("校验验证码失败: {}", JSONUtil.toJsonStr(data));
    }

    @Override
    public void successHandle(Map<String, Object> data) {
        // TODO 验证码成功处理
    }

    @Override
    public int order() {
        return 1;
    }
}
