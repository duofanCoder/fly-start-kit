package com.duofan.fly.framework.security.constraint.impl;

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


    @Override
    public void doCheck(Map<String, Object> data) throws LoginValidException {
        log.info("校验验证码成功");
    }

    @Override
    public boolean supportError(LoginValidException e) {
        return e instanceof FlyCaptchaValidException;
    }

    @Override
    public void errorHandle(Map<String, Object> data, LoginValidException e) {
        String captcha = data.getOrDefault(captchaParam, "").toString();
        
        // TODO 验证码成功处理
        System.out.println(data);
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
