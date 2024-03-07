package com.duofan.fly.framework.security.constraint.impl;

import com.duofan.fly.core.base.domain.permission.FlyToken;
import com.duofan.fly.framework.security.constraint.AbstractLoginService;
import com.duofan.fly.framework.security.constraint.FlyTokenService;
import com.duofan.fly.framework.security.exception.LoginFailException;
import com.duofan.fly.framework.security.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 默认登陆实现
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Slf4j
public class FlyDefaultLoginService extends AbstractLoginService {

    static final String LOGIN_FAIL_LOG = "登陆失败:  用户名称 = {} , 失败信息 = {}";
    static final String LOGIN_SUCCESS_LOG = "登陆成功:  用户名称 = {} ";

    public FlyDefaultLoginService(@Qualifier("delegatingLoginValidRepository") DelegatingLoginValidRepository loginValidRepository,
                                  SecurityProperties properties,
                                  AuthenticationProvider authenticationProvider, FlyTokenService tokenService) {
        super(loginValidRepository, properties, authenticationProvider, tokenService);
    }

    @Override
    public FlyToken login(Map<String, Object> data) {
        try {
            return super.login(data);
        } catch (Exception e) {
            for (LoginFailException.LoginFailStatus value : LoginFailException.LoginFailStatus.values()) {
                Class<?> clazz = value.getClazz();
                // 判断异常是否该类实例
                if (clazz.isInstance(e)) {
                    throw new LoginFailException(value);
                }
            }
            throw e;
        }
    }
}
