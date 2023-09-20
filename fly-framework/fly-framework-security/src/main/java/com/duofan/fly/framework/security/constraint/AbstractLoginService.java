package com.duofan.fly.framework.security.constraint;

import com.alibaba.fastjson2.JSONObject;
import com.duofan.fly.framework.security.constraint.impl.DelegatingLoginValidRepository;
import com.duofan.fly.framework.security.property.SecurityProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Slf4j
public abstract class AbstractLoginService implements FlyLoginService {

    private final FlyLoginValidRepository loginValidRepository = new DelegatingLoginValidRepository();
    @Resource
    private SecurityProperties properties;

    @Resource(type = AuthenticationProvider.class)
    private AuthenticationProvider authenticationProvider;

    @Override
    public JSONObject login(JSONObject data) throws RuntimeException {
        loginValidRepository.doCheck(data);
        UsernamePasswordAuthenticationToken unauthenticated =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        data.getString(properties.getLogin().getUsernameParameter()),
                        data.getString(properties.getLogin().getPasswordParameter())
                );
        Authentication authenticate = authenticationProvider.authenticate(unauthenticated);
        Object details = authenticate.getDetails();
        Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
        }
        return null;
    }
}
