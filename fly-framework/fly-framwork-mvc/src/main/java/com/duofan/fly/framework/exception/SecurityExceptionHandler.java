package com.duofan.fly.framework.exception;

import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.enums.FlyHttpStatus;
import com.duofan.fly.framework.security.exception.FlySecurityException;
import com.duofan.fly.framework.security.exception.RegisterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.LoginException;

/**
 * 全局异常处理
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

    @ResponseBody
    @ExceptionHandler(FlySecurityException.class)
    public FlyResult handleValidException(FlySecurityException e) {
        log.warn("安全认证统一异常处理：{}", e.getMessage());
        return FlyResult.of(FlyHttpStatus.FAIL);
    }

    @ResponseBody
    @ExceptionHandler(LoginException.class)
    public FlyResult handleValidException(LoginException e) {
        log.warn("安全认证统一异常处理：{}", e.getMessage());
        return FlyResult.of(FlyHttpStatus.FAIL);
    }


    @ResponseBody
    @ExceptionHandler(RegisterException.class)
    public FlyResult handleValidException(RegisterException e) {
        log.warn("安全认证统一异常处理：{}", e.getMessage());
        return FlyResult.of(FlyHttpStatus.FAIL, e.getMessage());
    }


}
