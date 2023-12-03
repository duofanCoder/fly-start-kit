package com.duofan.fly.framework.security.constraint;

import cn.hutool.core.util.StrUtil;
import com.duofan.fly.core.base.domain.permission.FlyToken;
import com.duofan.fly.framework.security.constraint.impl.DelegatingLoginValidRepository;
import com.duofan.fly.framework.security.exception.LoginFailException;
import com.duofan.fly.framework.security.exception.LoginValidException;
import com.duofan.fly.framework.security.exception.loginValid.LoginParamException;
import com.duofan.fly.framework.security.property.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

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

    protected void loginParamValid(Map<String, Object> data) {
        if (!Boolean.logicalAnd(data.containsKey(obtainUsernameParam()),
                data.containsKey(obtainPasswordParam()))) {
            throw new LoginParamException(LoginFailException.LoginFailStatus.PARAM_ERROR);
        }
        if (!StrUtil.isAllNotBlank(data.get(obtainUsernameParam()).toString(),
                data.get(obtainPasswordParam()).toString())) {
            throw new LoginParamException(LoginFailException.LoginFailStatus.PARAM_ERROR);
        }
    }

    @Override
    public FlyToken login(Map<String, Object> data) throws RuntimeException {
        loginParamValid(data);
        val username = data.get(obtainUsernameParam()).toString();
        val password = data.get(obtainPasswordParam()).toString();
        val isRemember = data.getOrDefault(obtainIsRememberParam(),"0").toString();

        data.putIfAbsent("username", username);
        data.putIfAbsent("password", password);


        FlyLoginUser loginUser = null;


        try {
            loginValidRepository.doCheck(data);
            loginUser = authenticate(username, password);
            loginUser.setIsRemember(isRemember);
        } catch (LoginValidException e) {
            loginValidRepository.doErrHandle(data, e);
            log.info("{}", e.getMessage());
            throw e;
        } catch (AuthenticationException e) {
            log.info("{}", e.getMessage());
            loginValidRepository.doErrHandle(data, e);
            throw e;
        } catch (Exception e) {
            log.info("{}", e.getMessage());
        }
        loginValidRepository.doSuccessHandle(data);
        return tokenService.create(loginUser);
    }


    protected FlyLoginUser authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken unauthenticated =
                UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        Authentication authenticate = authenticationProvider.authenticate(unauthenticated);
        return (FlyLoginUser) authenticate.getPrincipal();
    }


    protected String obtainUsernameParam() {
        return properties.getLogin().getUsernameParameter();
    }

    protected String obtainPasswordParam() {
        return properties.getLogin().getPasswordParameter();
    }    protected String obtainIsRememberParam() {
        return properties.getLogin().getIsRememberParameter();
    }
}
