package com.duofan.fly.framework.security.constraint;

import com.alibaba.fastjson2.JSONObject;
import com.duofan.fly.framework.security.property.SecurityProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Slf4j
public abstract class AbstractLoginService implements FlyLoginService {

    @Resource(lookup = "delegatingLoginValidRepository")
    private FlyLoginValidRepository loginValidRepository;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private SecurityProperties properties;

    @Override
    public JSONObject login(JSONObject data) {
        loginValidRepository.doCheck(data);
        UsernamePasswordAuthenticationToken unauthenticated =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        data.getString(properties.getLogin().getUsernameParameter()),
                        data.getString(properties.getLogin().getPasswordParameter())
                );
        Authentication authenticate = authenticationManager.authenticate(unauthenticated);
        return null;
    }
}
