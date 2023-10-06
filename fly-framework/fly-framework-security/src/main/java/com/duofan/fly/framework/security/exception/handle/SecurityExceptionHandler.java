package com.duofan.fly.framework.security.exception.handle;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.enums.FlyHttpStatus;
import com.duofan.fly.framework.security.exception.FlySecurityException;
import com.duofan.fly.framework.security.exception.LoginFailException;
import com.duofan.fly.framework.security.exception.RegisterException;
import com.duofan.fly.framework.security.exception.loginValid.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;
import java.util.List;

/**
 * 安全全局异常处理
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/24
 */

@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {
    private final String AUTH_EXCEPTION_LOG = "安全认证统一异常处理：{}";


    @ResponseBody
    @ExceptionHandler(TokenExpiredException.class)
    public FlyResult handleTokenExpiredException(TokenExpiredException e) {
        log.warn(AUTH_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.UNAUTHORIZED);
    }

    @ResponseBody
    @ExceptionHandler(LoginFailException.class)
    public FlyResult handleLoginFailException(LoginFailException e) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ObjUtil.notEqual(CollUtil.indexOf(List.of(LoginFailException.LoginFailStatus.PASSWORD_ERROR, LoginFailException.LoginFailStatus.USERNAME_NOT_FOUND,
                        LoginFailException.LoginFailStatus.PARAM_ERROR),
                loginFailStatus -> loginFailStatus.equals(e.getStatus())), -1)) {
            log.info(AUTH_EXCEPTION_LOG, e.getMessage());
            return FlyResult.of(FlyHttpStatus.FAIL, "账号或密码错误");
        }
        if (ObjUtil.notEqual(CollUtil.indexOf(List.of(LoginFailException.LoginFailStatus.ACCOUNT_LOCKED),
                loginFailStatus -> loginFailStatus.equals(e.getStatus())), -1)) {
            log.info(AUTH_EXCEPTION_LOG, e.getMessage());
            return FlyResult.of(FlyHttpStatus.FAIL, "账号已锁，无法登录");
        }
        log.warn(AUTH_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.FAIL);
    }

    @ResponseBody
    @ExceptionHandler(LoginException.class)
    public FlyResult handleValidException(LoginException e) {
        log.warn(AUTH_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.FAIL);
    }


    @ResponseBody
    @ExceptionHandler(RegisterException.class)
    public FlyResult handleValidException(RegisterException e) {
        log.warn(AUTH_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.FAIL, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(FlySecurityException.class)
    public FlyResult handleValidException(FlySecurityException e) {
        log.warn(AUTH_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.FAIL);
    }

}
