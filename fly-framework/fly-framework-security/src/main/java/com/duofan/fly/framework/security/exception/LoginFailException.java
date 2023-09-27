package com.duofan.fly.framework.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/27
 */
@Getter
public class LoginFailException extends FlySecurityException {


    private final LoginFailStatus status;

    public LoginFailException(LoginFailStatus status) {
        super(status.getText());
        this.status = status;
    }

    public LoginFailException(String message, LoginFailStatus status) {
        super(message);
        this.status = status;
    }

    @Getter
    @AllArgsConstructor
    public static enum LoginFailStatus {
        USERNAME_NOT_FOUND("0", "账号不存在", AccountNotFoundException.class),
        PASSWORD_ERROR("1", "密码错误", BadCredentialsException.class),
        ACCOUNT_EXPIRED("3", "账户过期", AccountNotFoundException.class),
        ACCOUNT_STATUS_ERROR("4", "账户状态异常", AccountStatusException.class),
        ACCOUNT_DISABLED("5", "账户未激活", DisabledException.class),
        ACCOUNT_LOCKED("6", "账户已锁", AccountLockedException.class),
        ERROR("6", "认证错误", AuthenticationException.class);

        private final String code;
        private final String text;
        private final Class<?> clazz;
    }
}
