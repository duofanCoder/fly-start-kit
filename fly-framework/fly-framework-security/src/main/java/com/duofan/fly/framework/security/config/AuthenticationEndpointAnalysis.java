package com.duofan.fly.framework.security.config;

import com.duofan.fly.core.base.access.FlyAccessInfo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

//PreAuthorizeAuthorizationManager

    @Resource
    private ApplicationContext applicationContext;

    private Map<String, Module> modules;
//    private List<Operation> grantToAll;

    @PostConstruct
    public void analysis() {
        System.out.println("hello");
        log.info("认证端点模块分析开始");
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);
        for (Map.Entry<String, Object> entry : controllers.entrySet()) {
            Object controller = entry.getValue();
            Class<?> clazz = controller.getClass();
            FlyAccessInfo annotation = AnnotationUtils.findAnnotation(clazz, FlyAccessInfo.class);
            if (annotation == null) continue;
            System.out.println(annotation.system());
            Method[] controllerMethods = controller.getClass().getDeclaredMethods();
            List<Method> flyMethods = Arrays.stream(controllerMethods).filter(m -> m.isAnnotationPresent(FlyAccessInfo.class)).toList();
            for (Method flyMethod : flyMethods) {
                System.out.println(flyMethod.getName());
            }
        }

//        AnnotationUtils.findAnnotation()

    }

}
