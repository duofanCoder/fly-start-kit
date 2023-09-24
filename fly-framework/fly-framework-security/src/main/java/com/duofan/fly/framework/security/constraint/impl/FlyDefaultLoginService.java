package com.duofan.fly.framework.security.constraint.impl;

import com.alibaba.fastjson2.JSONObject;
import com.duofan.fly.framework.security.constraint.AbstractLoginService;
import com.duofan.fly.framework.security.property.SecurityProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;

/**
 * 默认登陆实现
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
public class FlyDefaultLoginService extends AbstractLoginService {

    public FlyDefaultLoginService(@Qualifier("delegatingLoginValidRepository") DelegatingLoginValidRepository loginValidRepository, SecurityProperties properties, AuthenticationProvider authenticationProvider) {
        super(loginValidRepository, properties, authenticationProvider);
    }

    @Override
    public JSONObject login(JSONObject data) {
        return super.login(data);
    }
}