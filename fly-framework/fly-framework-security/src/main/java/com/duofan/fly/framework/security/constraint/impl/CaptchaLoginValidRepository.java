package com.duofan.fly.framework.security.constraint.impl;

import com.alibaba.fastjson2.JSONObject;
import com.duofan.fly.framework.security.constraint.FlyLoginValidRepository;
import com.duofan.fly.framework.security.exception.LoginValidException;
import lombok.extern.slf4j.Slf4j;

/**
 * 验证码校验器
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Slf4j
public class CaptchaLoginValidRepository implements FlyLoginValidRepository {


    @Override
    public void doCheck(JSONObject data) throws LoginValidException {
        log.info("校验验证码成功");
    }

    @Override
    public int order() {
        return 1;
    }
}
