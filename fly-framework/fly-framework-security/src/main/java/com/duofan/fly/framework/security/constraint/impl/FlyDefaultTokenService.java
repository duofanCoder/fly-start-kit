package com.duofan.fly.framework.security.constraint.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.duofan.fly.core.base.domain.permission.FlyToken;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.property.SecurityProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/24
 */
@Slf4j
public class FlyDefaultTokenService implements FlyTokenService {

    @Resource
    private SecurityProperties properties;

    @Override
    public FlyToken create(Map<String, Object> payloads) {
        String token = JWTUtil.createToken(payloads, jwtSigner());
        return new FlyToken().setToken(token)
                .setPrefix("x-access-token");
    }

    @Override
    public Map<String, Object> parse(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        JWTPayload payload = jwt.getPayload();
        JSONObject claimsJson = payload.getClaimsJson();
        return null;
    }

    private JWTSigner jwtSigner() {
        return JWTSignerUtil.createSigner(properties.getToken().getAlgorithm(),
                properties.getToken().getKey().getBytes());
    }
}
