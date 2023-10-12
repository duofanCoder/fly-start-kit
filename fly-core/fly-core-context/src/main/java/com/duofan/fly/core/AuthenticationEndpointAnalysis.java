package com.duofan.fly.core;

import cn.hutool.core.util.StrUtil;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.domain.FlyApi;
import com.duofan.fly.core.domain.FlyModule;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * web controller 端点信息生成
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/14
 */
@Slf4j
@Component
public class AuthenticationEndpointAnalysis {

    @Resource
    private ApplicationContext applicationContext;

    private final static Map<String, FlyModule> modules = new ConcurrentHashMap<>();
    // key => module + method
    private final static Map<String, FlyResourceInfo> apiInfos = new ConcurrentHashMap<>();

    // 不需要认证就可以访问的接口
    private final static List<String> whiteApis = new ArrayList<>();

    public List<String> getWhiteApis() {
        return whiteApis;
    }

    public static Map<String, FlyModule> getEndpointModules() {
        return Collections.unmodifiableMap(modules);
    }

    public static FlyResourceInfo getFullApiInfo(String module, String op) {
        FlyResourceInfo info = new FlyResourceInfo();
        info.setModule(module)
                .setOp(op)
                .setModuleName(modules.get(module).getModuleName())
                .setOpName(modules.get(module).getApis().get(op).getOpName())
                .setGrantToAll(modules.get(module).getApis().get(op).isGrantAll())
                .setNeedAuthenticated(modules.get(module).getApis().get(op).isNeedAuthenticated())
                .setDescription(modules.get(module).getApis().get(op).getDescription());
        return info;
    }

    /**
     * module.op
     *
     * @param moduleOp
     * @return
     */
    public static FlyResourceInfo getApiInfo(String moduleOp) {
        return apiInfos.computeIfAbsent(moduleOp, (k) -> getFullApiInfo(
                StrUtil.subBefore(moduleOp, ".", true),
                StrUtil.subAfter(moduleOp, ".", true)
        ));
    }

    private void analysis() {
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);
        for (Map.Entry<String, Object> entry : controllers.entrySet()) {
            Object controller = entry.getValue();
            Class<?> clazz = ClassUtils.getUserClass(controller.getClass());
            FlyAccessInfo annotation = AnnotationUtils.findAnnotation(clazz, FlyAccessInfo.class);
            String reqRoot = getAnnotation(clazz);
            if (annotation == null) continue;
            FlyModule module = new FlyModule();
            ConcurrentHashMap<String, FlyApi> apis = new ConcurrentHashMap<>();
            module.setModuleName(StrUtil.emptyToDefault(annotation.moduleName(), clazz.getName()))
                    .setModule(clazz.getName())
                    .setDescription(annotation.description())
                    .setSystem(annotation.system())
                    .setApis(apis);
            Method[] controllerMethods = clazz.getDeclaredMethods();
            // 注解的方法
            List<Method> flyMethods = Arrays.stream(controllerMethods).filter(m -> m.isAnnotationPresent(FlyAccessInfo.class)).toList();
            for (Method flyMethod : flyMethods) {
                FlyAccessInfo methodAnnotation = flyMethod.getAnnotation(FlyAccessInfo.class);
                String reqUrl = getAnnotation(flyMethod);
                FlyApi api = new FlyApi()
                        .setOpName(StrUtil.emptyToDefault(methodAnnotation.opName(), flyMethod.getName()))
                        .setOp(flyMethod.getName())
                        .setDescription(methodAnnotation.description())
                        .setGrantAll(methodAnnotation.isGrantToAll())
                        .setNeedAuthenticated(methodAnnotation.needAuthenticated())
                        .setRequestUrl((reqRoot + "/" + reqUrl).replace("//", "/"));
                apis.put(flyMethod.getName(), api);
                if (!api.isNeedAuthenticated()) {
                    whiteApis.add(api.getRequestUrl());
                }
            }
            modules.putIfAbsent(module.getModule(), module);
        }


    }

    private String getAnnotation(AnnotatedElement annotationEle) {
        Annotation annotation = AnnotationUtils.findAnnotation(annotationEle, GetMapping.class) != null ?
                AnnotationUtils.findAnnotation(annotationEle, GetMapping.class) :
                AnnotationUtils.findAnnotation(annotationEle, PostMapping.class) != null ?
                        AnnotationUtils.findAnnotation(annotationEle, PostMapping.class) :
                        AnnotationUtils.findAnnotation(annotationEle, RequestMapping.class) != null ?
                                AnnotationUtils.findAnnotation(annotationEle, RequestMapping.class) : null;
        if (annotation instanceof GetMapping an) {
            return an.value()[0];
        } else if (annotation instanceof PostMapping getMapping) {
            return getMapping.value()[0];
        } else if (annotation instanceof RequestMapping postMapping) {
            return postMapping.value()[0];
        }
        return null;
    }

    @PostConstruct
    public void run() {
        log.info(LogConstant.COMPONENT_LOG, "认证端点分析", "启动");
        analysis();
        log.info(LogConstant.COMPONENT_LOG, "认证端点分析", "完毕");
        modules.forEach(
                (module, info) -> {
                    log.info(LogConstant.COMPONENT_LOG + "{}", "认证端点分析", "模块【" + info.getModuleName(), "】加载完毕");
                }
        );
    }


    public static Map<String, FlyModule> listOps() {
        return Collections.unmodifiableMap(modules);
    }

}
