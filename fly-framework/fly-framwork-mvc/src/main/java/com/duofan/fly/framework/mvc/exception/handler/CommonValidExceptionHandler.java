package com.duofan.fly.framework.mvc.exception.handler;

import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.enums.FlyHttpStatus;
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
        return FlyResult.of(FlyHttpStatus.BAD_REQUEST);
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
}
