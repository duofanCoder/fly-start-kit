package com.duofan.fly.framework.security.constraint.impl;

import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson2.JSONObject;
import com.duofan.fly.core.base.domain.permission.FlyToken;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/24
 */
@Slf4j
public class FlyDefaultTokenService implements FlyTokenService {
    @Override
    public FlyToken create(JSONObject payloads) {
        
        JWTUtil.createToken(map, "asd".getBytes());
        return null;
    }

    @Override
    public JSONObject parse(String token) {
        return null;
    }
}
