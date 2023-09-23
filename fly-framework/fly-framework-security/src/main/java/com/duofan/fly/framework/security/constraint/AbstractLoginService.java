package com.duofan.fly.framework.security.constraint;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.duofan.fly.framework.security.constraint.impl.DelegatingLoginValidRepository;
import com.duofan.fly.framework.security.property.SecurityProperties;
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

    private final DelegatingLoginValidRepository loginValidRepository;
    private final SecurityProperties properties;

    private final AuthenticationProvider authenticationProvider;


    public AbstractLoginService(DelegatingLoginValidRepository loginValidRepository, SecurityProperties properties, AuthenticationProvider authenticationProvider) {
        this.loginValidRepository = loginValidRepository;
        this.properties = properties;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public JSONObject login(JSONObject data) throws RuntimeException {
        loginValidRepository.doCheck(data);
        UsernamePasswordAuthenticationToken unauthenticated =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        data.getString(properties.getLogin().getUsernameParameter()),
                        data.getString(properties.getLogin().getPasswordParameter())
                );
        Authentication authenticate = authenticationProvider.authenticate(unauthenticated);
        FlyLoginUser user = (FlyLoginUser) authenticate.getDetails();
        Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            System.out.println(role);
        }
        return JSON.parseObject(JSONObject.toJSONString(user, JSONWriter.Feature.FieldBased));
    }
}
