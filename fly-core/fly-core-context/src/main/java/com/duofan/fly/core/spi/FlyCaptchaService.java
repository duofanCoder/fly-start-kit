package com.duofan.fly.core.spi;

import cn.hutool.captcha.ICaptcha;
import jakarta.servlet.http.HttpServletRequest;

public interface FlyCaptchaService {
    /**
     * 通用校验接口使用：创建验证码
     */
    ICaptcha createCaptcha(HttpServletRequest request);


    /**
     * 通用校验接口使用：校验验证码
     */
    boolean checkCaptcha(String code, HttpServletRequest request);

    /**
     * 通用校验接口使用：校验验证码
     *
     * @param code 验证码
     */
    boolean checkCaptcha(String code);


}