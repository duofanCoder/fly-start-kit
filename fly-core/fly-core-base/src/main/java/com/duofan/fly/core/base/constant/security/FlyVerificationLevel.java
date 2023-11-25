package com.duofan.fly.core.base.constant.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资源安全校验级别
 * TODO 对应安全  使用对应的校验方式
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/25
 */
@Getter
@AllArgsConstructor
public enum FlyVerificationLevel {
    // 邮箱校验
    EMAIL("0", "邮箱校验", "邮箱校验"),
    // 手机号校验
    PHONE("1", "手机号校验", "手机号校验"),
    // 图片验证码校验
    CAPTCHA("2", "图片验证码校验", "图片验证码校验");


    private final String code;
    private final String msg;
    private final String desc;
}
