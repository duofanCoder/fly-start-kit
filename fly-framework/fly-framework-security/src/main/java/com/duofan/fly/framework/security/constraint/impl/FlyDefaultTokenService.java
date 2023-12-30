package com.duofan.fly.framework.security.constraint.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.*;
import cn.hutool.jwt.signers.AlgorithmUtil;
import cn.hutool.jwt.signers.HMacJWTSigner;
import com.duofan.fly.core.base.constant.security.SecurityConstant;
import com.duofan.fly.core.base.domain.permission.FlyToken;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.base.enums.BooleanDict;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import com.duofan.fly.framework.security.constraint.FlyLoginUser;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.property.SecurityProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/24
 */
@Slf4j
@Component
public class FlyDefaultTokenService implements FlyTokenService {
    @Resource
    private SecurityProperties properties;

    @Resource
    private FlyCacheService cacheService;

    @Override
    public FlyToken create(FlyLoginUser loginUser) {
        String token = new JWT()
                .setHeader(JWTHeader.ALGORITHM, properties.getToken().getAlgorithm())
                .setHeader(JWTHeader.TYPE, "jwt")
                .setSubject(loginUser.getUsername())
                .setCharset(CharsetUtil.CHARSET_UTF_8)
                .setPayload("roles", loginUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .setPayload("currentRoleNo", Optional.ofNullable(loginUser.getCurrentRoleNo()).orElse(loginUser.getRoleList().get(0).getRoleNo()))
                .setIssuedAt(new Date())
                // 注销 续签信息 保存在redis
                .setExpiresAt(DateUtil.offsetDay(new Date(), 7))
                .setSigner(AlgorithmUtil.getAlgorithm(properties.getToken().getAlgorithm()),
                        properties.getToken().getSignSecret().getBytes())
                .sign();
        String loginTokenKey = CacheKeyUtils.getLoginTokenKey(loginUser.getUsername(), token);
        SecurityProperties.TokenProperties tokenProperties = properties.getToken();
        Duration activateTime = tokenProperties.getExpired();
        if (BooleanDict.YES.getCode().equals(loginUser.getIsRemember())) {
            activateTime = activateTime.plusDays(7);
        }
        // TODO 登录身份凭证有效时长 配置化
        cacheService.set(loginTokenKey, loginUser, activateTime);
        return new FlyToken().setToken(token)
                .setExpiredAt(DateUtil.offsetHour(new Date(), Math.toIntExact(activateTime.toHours())))
                .setHeaderKey(SecurityConstant.TOKEN_HEADER_KEY);
    }

    @Override
    public Map<String, Object> parse(String token) {
        JWT jwt = new JWT().parse(token);
        JWTPayload payload = jwt.getPayload();
        return payload.getClaimsJson().toBean(new TypeReference<Map<String, Object>>() {
        });
    }


    // 测试签名时间
    public static void main(String[] args) throws InterruptedException {

        FlyLoginUser loginUser = new FlyLoginUser(new FlyUser().setUsername("hhello"), null, null);

        String token = new JWT()
                .setHeader(JWTHeader.ALGORITHM, "HS256")
                .setHeader(JWTHeader.TYPE, "jwt")
                .setSubject(loginUser.getUsername())
                .setCharset(CharsetUtil.CHARSET_UTF_8)
                .setPayload("roles", loginUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .setIssuedAt(new Date())
                .setExpiresAt(DateUtil.offsetSecond(new Date(), 3))
                .setNotBefore(DateUtil.offsetSecond(new Date(), 2))
                .setSigner(AlgorithmUtil.getAlgorithm("HS256"),
                        "properties.getToken().getSignSecret()".getBytes())
                .sign();
        System.out.println(token);

        Thread.sleep(2000);

        System.out.println("过期验证:" + JWTValidator.of(token).validateDate(DateUtil.date(), 0));


        boolean verify = JWT.of(token)
                .verify(new HMacJWTSigner(AlgorithmUtil.getAlgorithm("HS256"),
                        "properties.getToken().getSignSecret()".getBytes()));

        System.out.println("篡改验证:" + verify);
        JWT jwt = new JWT().parse(token);
        JWTPayload payload = jwt.getPayload();
        String[] roles = jwt.getPayload("roles").toString().split(",");
        JSONObject claimsJson = payload.getClaimsJson();
        System.out.println(Arrays.toString(roles));
        System.out.println(claimsJson);
    }

    /**
     * 校验TOKEN是否篡改
     *
     * @param token
     * @return
     */
    @Override
    public boolean verify(String token) {
        try {
            return JWTUtil.verify(token, properties.getToken().getSignSecret().getBytes());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void refresh(String token) {
        cacheService.expireAt(token, DateUtil.offsetHour(new Date(), 24));
    }

    @Override
    public void delete(String token) {
        Map<String, Object> info = parse(token);

        String username = info.get("sub").toString();
        CacheKeyUtils.getLoginTokenKey(username, token);
        // TODO 处理防止无状态jwt 多次登陆返回token
        cacheService.delete(token);
    }


    /**
     * 校验TOKEN是否过期
     *
     * @param token
     * @return
     */
    @Override
    public boolean validate(String token) {
        try {
            JWTValidator.of(token).validateDate(DateUtil.date(), 60);
        } catch (ValidateException e) {
            return false;
        }
        return true;
    }


}
