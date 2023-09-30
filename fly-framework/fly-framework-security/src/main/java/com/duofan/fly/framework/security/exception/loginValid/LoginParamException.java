package com.duofan.fly.framework.security.exception.loginValid;

import com.duofan.fly.framework.security.exception.LoginFailException;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/29
 */
public class LoginParamException extends LoginFailException {

    public LoginParamException(LoginFailStatus status) {
        super(status);
    }

    public LoginParamException(String message, LoginFailStatus status) {
        super(message, status);
    }
}
