package com.duofan.fly.framework.security.config;

import cn.hutool.core.util.StrUtil;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.domain.FlyApi;
import com.duofan.fly.core.domain.FlyModule;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
public class AuthenticationEndpointAnalysis implements CommandLineRunner {

    @Resource
    private ApplicationContext applicationContext;

    private final static Map<String, FlyModule> modules = new ConcurrentHashMap<>();
    private final static Map<String, FlyResourceInfo> apiInfos = new ConcurrentHashMap<>();

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
            if (annotation == null) continue;
            FlyModule module = new FlyModule();
            ConcurrentHashMap<String, FlyApi> apis = new ConcurrentHashMap<>();
            module.setModuleName(annotation.moduleName())
                    .setModule(clazz.getName())
                    .setDescription(annotation.description())
                    .setSystem(annotation.system())
                    .setApis(apis);
            Method[] controllerMethods = clazz.getDeclaredMethods();
            // 注解的方法
            List<Method> flyMethods = Arrays.stream(controllerMethods).filter(m -> m.isAnnotationPresent(FlyAccessInfo.class)).toList();
            for (Method flyMethod : flyMethods) {
                FlyAccessInfo methodAnnotation = flyMethod.getAnnotation(FlyAccessInfo.class);
                apis.put(flyMethod.getName(), new FlyApi()
                        .setOpName(methodAnnotation.opName())
                        .setOp(flyMethod.getName())
                        .setDescription(methodAnnotation.description())
                        .setGrantAll(methodAnnotation.isGrantToAll())
                );
            }
            modules.putIfAbsent(module.getModule(), module);
        }


    }

    @Override
    public void run(String... args) {
        log.info(LogConstant.COMPONENT_LOG, "认证端点分析", "启动");
        analysis();
        log.info(LogConstant.COMPONENT_LOG, "认证端点分析", "完毕");
        modules.forEach(
                (module, info) -> {
                    log.info(LogConstant.COMPONENT_LOG + "{}", "认证端点分析", "模块【" + info.getModuleName(), "】 加载完毕");
                }
        );
    }


    public static Map<String, FlyModule> listOps() {
        return Collections.unmodifiableMap(modules);
    }

}
