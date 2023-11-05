package com.duofan.fly.framework.mvc.exception.handler;

import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.exception.FlyAccessVerifyException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 可以操作异常处理
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/5
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class SuspiciousExceptionHandler {

    @Resource
    private HttpServletRequest request;

    @ResponseBody
    @ExceptionHandler(FlyAccessVerifyException.class)
    public FlyResult handleFlyAccessVerifyException(FlyAccessVerifyException e) {
        // 获取请求人的ip
        String clientIp = request.getRemoteAddr();
        log.warn(LogConstant.SUSPICIOUS_OPERATION_LOG, request.getRequestURI(), clientIp, e.getMessage());
        return FlyResult.of(e.getCode(), e.getMessage());
    }


}
