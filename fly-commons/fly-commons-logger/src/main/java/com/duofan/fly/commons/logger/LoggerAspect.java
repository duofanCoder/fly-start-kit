package com.duofan.fly.commons.logger;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONUtil;
import com.duofan.fly.core.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 日志切面
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/15
 */


@Slf4j
@Aspect
@Component
public class LoggerAspect {

    private static ThreadLocal<Long> startTime = new ThreadLocal();

    @Pointcut("@annotation(com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo)")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        HashMap<Object, Object> result = new HashMap<>(4);
        result.put("Url", request.getRequestURL().toString());
        result.put("Ip", WebUtils.getIp(request));
        result.put("Method", request.getMethod());
        if (!HttpMethod.GET.toString().equals(request.getMethod())) result.put("Body", getRequestBody(joinPoint));
        result.put("Controller", getRequestController(joinPoint));
        log.info("Request:{}", cutOut(result));
    }

    private String getRequestController(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName();
    }

    private HashMap<String, Object> getRequestBody(JoinPoint joinPoint) {
        Class[] filterParamClazz = {MultipartFile.class, File.class};
        Object[] args = joinPoint.getArgs();
        HashMap<String, Object> result = new HashMap<>(args.length);
        if (args.length == 0) return null;
        Supplier<Stream<Class>> clazzStreamSupplier = () -> Arrays.stream(filterParamClazz);
        // 获取方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            String name = parameterNames[i];
            Object value = args[i];
            Stream<Class> clazzStream = clazzStreamSupplier.get();
            if (clazzStream.anyMatch(clazz -> clazz.isInstance(value))) result.put(name, "Parse type not supported");
            else
                result.put(name, value);
        }
        return result;
    }

    @AfterReturning(returning = "retVal", pointcut = "log()")
    public void logAfterReturning(JoinPoint joinPoint, Object retVal) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        long execTime = System.currentTimeMillis() - startTime.get();
        HashMap<Object, Object> result = new HashMap<>();
        result.put("Url", request.getRequestURL().toString());
        result.put("Time", execTime + "ms");
        result.put("Result", retVal);
        log.info("Response:{}", cutOut(result));
    }

    private String cutOut(Map map) {
        int length = 1024;
        List<String> colArr = Arrays.asList("Method", "Url", "Controller", "Body", "Result", "Time", "Ip");
        ArrayList<String> returnResult = new ArrayList<>();
        for (String col : colArr)
            if (map.containsKey(col)) {
                String colValue = "";
                try {
                    colValue = StrUtil.blankToDefault(JSONUtil.toJsonStr(map.get(col)), "");
                } catch (JSONException e) {
                    log.info("{}", e.getMessage());
                }
                if (colValue.length() > length) colValue = colValue.substring(0, length);
                returnResult.add(String.format("\"%s\":%s", col, colValue));
            }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(CollectionUtil.join(returnResult, ","));
        builder.append("}");
        return builder.toString();
    }

}
