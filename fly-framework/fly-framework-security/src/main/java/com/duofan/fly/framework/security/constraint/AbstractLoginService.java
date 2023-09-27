package com.duofan.fly.framework.security.constraint;

import com.duofan.fly.core.base.domain.permission.FlyToken;
import com.duofan.fly.framework.security.constraint.impl.DelegatingLoginValidRepository;
import com.duofan.fly.framework.security.property.SecurityProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Map;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Slf4j
public abstract class AbstractLoginService implements FlyLoginService {
    static final String LOGIN_FAIL_LOG = "登陆失败:  用户名称 = {} , 失败信息 = {}";
    static final String LOGIN_SUCCESS_LOG = "登陆成功:  用户名称 = {} ";

    private final DelegatingLoginValidRepository loginValidRepository;
    private final SecurityProperties properties;

    private final AuthenticationProvider authenticationProvider;

    private final FlyTokenService tokenService;

    public AbstractLoginService(DelegatingLoginValidRepository loginValidRepository, SecurityProperties properties, AuthenticationProvider authenticationProvider, FlyTokenService tokenService) {
        this.loginValidRepository = loginValidRepository;
        this.properties = properties;
        this.authenticationProvider = authenticationProvider;
        this.tokenService = tokenService;
    }

    @SneakyThrows
    @Override
    public FlyToken login(Map<String, Object> data) throws RuntimeException {
        loginValidRepository.doCheck(data);
        val username = data.get(properties.getLogin().getUsernameParameter()).toString();
        val password = data.get(properties.getLogin().getPasswordParameter()).toString();
        FlyLoginUser loginUser = authenticate(username, password);
        return tokenService.create(loginUser);
    }

    protected FlyLoginUser authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken unauthenticated =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        username,
                        password
                );
        Authentication authenticate = authenticationProvider.authenticate(unauthenticated);
        return (FlyLoginUser) authenticate.getPrincipal();
    }
}
