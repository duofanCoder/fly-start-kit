package com.duofan.fly.framework.mvc.exception.handler;

import cn.hutool.core.util.StrUtil;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.exception.FlyConstraintException;
import com.duofan.fly.core.base.domain.exception.FlyInternalException;
import com.duofan.fly.core.base.enums.FlyHttpStatus;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 处理入参异常
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/8
 */
@Slf4j
@RestControllerAdvice
public class CommonValidExceptionHandler {

    private final String VALID_EXCEPTION_LOG = "入参校验统一异常处理：{}";


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public FlyResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn(VALID_EXCEPTION_LOG, e.getMessage());
        // 返回第一个错误显示错误内容和字段
        e.getBindingResult().getAllErrors().forEach(error -> {
            log.warn("入参校验统一异常处理：{}，字段：{}，错误内容：{}", e.getMessage(), error.getCodes()[1], error.getDefaultMessage());
        });

        String msg = StrUtil.format("{} {}", Objects.requireNonNull(
                        e.getBindingResult().getAllErrors().get(0).getCodes())[1],
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return FlyResult.of(FlyHttpStatus.BAD_REQUEST).setMsg(msg);
    }

    @ResponseBody
    @ExceptionHandler(FlyConstraintException.class)
    public FlyResult handleFlyConstraintException(FlyConstraintException e) {
        log.warn(VALID_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ServletRequestBindingException.class)
    public FlyResult handleServletRequestBindingException(ServletRequestBindingException e) {
        log.warn(VALID_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public FlyResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn(VALID_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public FlyResult handleBindException(BindException e) {
        log.warn(VALID_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public FlyResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn(VALID_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public FlyResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.warn(VALID_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.BAD_REQUEST).setMsg("请求类型Content-Type不支持");
    }

    @ResponseBody
    @ExceptionHandler(ServletException.class)
    public FlyResult handleServletException(ServletException e) {
        log.warn(VALID_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.FAIL);
    }

    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    public FlyResult handleIllegalStateException(IllegalStateException e) {
        log.warn(VALID_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public FlyResult handleException(Exception e) {
        log.warn(VALID_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.FAIL);
    }

    @ResponseBody
    @ExceptionHandler(FlyInternalException.class)
    public FlyResult handleFlyInternalException(FlyInternalException e) {
        log.warn(VALID_EXCEPTION_LOG, e.getMessage());
        return FlyResult.of(FlyHttpStatus.Internal_ERROR);
    }


}
