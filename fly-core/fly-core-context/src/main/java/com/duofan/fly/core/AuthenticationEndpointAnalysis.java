package com.duofan.fly.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Tuple;
import cn.hutool.core.util.StrUtil;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.exception.FlySpecificationException;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.domain.FlyApi;
import com.duofan.fly.core.domain.FlyModule;
import com.duofan.fly.core.utils.MappingUtils;
import com.duofan.fly.core.utils.PermissionStrUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;

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
public class AuthenticationEndpointAnalysis {

    
    private final ApplicationContext applicationContext;

    private final static Map<String, FlyModule> modules = new ConcurrentHashMap<>();
    // 懒加载 key => module + method
    private final static Map<String, FlyResourceInfo> apiInfos = new ConcurrentHashMap<>();

    // 所有的flyApi
    private final static List<FlyApi> allApis = new ArrayList<>();

    // 不需要认证就可以访问的接口
    private final static List<FlyApi> whiteApis = new ArrayList<>();

    // 授权所有认证用户
    private final static List<FlyApi> grantAlleApis = new ArrayList<>();

    public AuthenticationEndpointAnalysis(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static Collection<FlyApi> getWhiteApis() {
        return CollUtil.unmodifiable(whiteApis);
    }

    public Collection<FlyApi> getGrantAlleApis() {
        return CollUtil.unmodifiable(grantAlleApis);
    }

    public static Map<String, FlyModule> getEndpointModules() {
        return Collections.unmodifiableMap(modules);
    }

    public static FlyResourceInfo getFullApiInfo(String module, String op) {
        FlyResourceInfo info = new FlyResourceInfo();
        info.setModule(module).setOp(op).setModuleName(modules.get(module).getModuleName()).setOpName(modules.get(module).getApis().get(op).getOpName()).setGrantToAll(modules.get(module).getApis().get(op).isGrantAll()).setNeedAuthenticated(modules.get(module).getApis().get(op).isNeedAuthenticated()).setDescription(modules.get(module).getApis().get(op).getDescription());
        return info;
    }

    /**
     * 懒加载
     * 接口使用过之后才会有存入的到apiInfos
     *
     * @param moduleOp
     * @return
     */
    public static FlyResourceInfo getApiInfo(String moduleOp) {
        return apiInfos.computeIfAbsent(moduleOp, (k) -> getFullApiInfo(PermissionStrUtils.module(moduleOp), PermissionStrUtils.operation(moduleOp)));
    }

    private void analysis() {
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);
        for (Map.Entry<String, Object> entry : controllers.entrySet()) {
            Object controller = entry.getValue();
            Class<?> clazz = ClassUtils.getUserClass(controller.getClass());
            FlyAccessInfo annotation = AnnotationUtils.findAnnotation(clazz, FlyAccessInfo.class);
            Tuple reqRoot = MappingUtils.getMappingUrl(clazz);
            // controller 没有FlyAccessInfo 注解
            if (annotation == null) {
                log.info("Controller类【{}】没有配置注解FlyAccessInfo,将无法访问该控制器的所有接口", clazz.getName());
                return;
            }

            // controller 没有配置requestMapping
            if (reqRoot == null) {
                reqRoot = new Tuple("", null);
            }

            FlyModule module = new FlyModule();
            ConcurrentHashMap<String, FlyApi> apis = new ConcurrentHashMap<>();
            module.setModuleName(StrUtil.emptyToDefault(annotation.moduleName(), clazz.getName())).setModule(clazz.getName()).setDescription(annotation.description()).setSystem(annotation.system()).setApis(apis);
            Method[] controllerMethods = clazz.getDeclaredMethods();
            // 注解的方法
            List<Method> flyMethods = Arrays.stream(controllerMethods).filter(m -> m.isAnnotationPresent(FlyAccessInfo.class)).toList();
            for (Method flyMethod : flyMethods) {
                FlyAccessInfo methodAnnotation = flyMethod.getAnnotation(FlyAccessInfo.class);
                Tuple reqUrl = MappingUtils.getMappingUrl(flyMethod);
                if (reqUrl == null){
                    throw new FlySpecificationException(StrUtil.format("如果是Controller层，请在类【{}】方法【{}】配置注解FlyAccessInfo", clazz.getName(), flyMethod.getName()));
                }
                FlyApi api = new FlyApi()
                        .setModule(module.getModule()).setModuleName(module.getModuleName())
                        .setOpName(StrUtil.emptyToDefault(methodAnnotation.opName(), flyMethod.getName()))
                        .setOp(flyMethod.getName()).setDescription(methodAnnotation.description())
                        .setGrantAll(methodAnnotation.isGrantToAll())
                        .setNeedAuthenticated(methodAnnotation.needAuthenticated())
                        .setRequestMethod(reqUrl.get(1))
                        .setRequestUrl((reqRoot.get(0) + "/" + reqUrl.get(0)).replace("//", "/"));
                apis.put(flyMethod.getName(), api);
                if (!api.isNeedAuthenticated()) {
                    whiteApis.add(api);
                }
                if (api.isGrantAll()) {
                    grantAlleApis.add(api);
                }
                allApis.add(api);
            }
            modules.putIfAbsent(module.getModule(), module);
        }


    }


    @PostConstruct
    public void run() {
        log.info(LogConstant.COMPONENT_LOG, "认证端点分析", "启动");
        analysis();
        modules.forEach((module, info) -> {
            log.info(LogConstant.COMPONENT_LOG + "{}", "认证端点分析", "模块【" + info.getModuleName(), "】加载完毕");
        });
        log.info(LogConstant.COMPONENT_LOG, "认证端点分析", "分析成功");

    }

    /**
     * 弃用不优雅
     *
     * @param map
     * @return
     */
    @Deprecated
    public static Map<String, Object> deepCopy(Map<String, Object> map) {
        Map<String, Object> copy = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                value = deepCopy((Map<String, Object>) value);
            } else if (value instanceof FlyModule module) {
                FlyModule copyModule = new FlyModule(module.getModuleName(), module.getModule(), module.getSystem(), module.getDescription());
                Map apis = module.getApis();
                copyModule.setApis(deepCopy(apis));
                value = copyModule;
            } else if (value instanceof FlyApi api) {
                value = new FlyApi(api.getModuleName(), api.getModule(), api.getSystem(), api.getModuleDescription(), api.getOpName(), api.getOp(), api.getDescription(), api.isGrantAll(), api.isActivated(), api.getRequestUrl(), api.isNeedAuthenticated());
            }
            copy.put(key, value);
        }
        return copy;
    }

    public static Map<String, FlyModule> listOps() {
        // 换个 深拷贝 modules的方法
        Map<String, FlyModule> copy = new HashMap<>();
        modules.forEach((k, v) -> {
            FlyModule module = new FlyModule();
            BeanUtil.copyProperties(v, module);
            ConcurrentHashMap<String, FlyApi> apis = new ConcurrentHashMap<>();
            v.getApis().forEach((k1, v1) -> {
                FlyApi api = new FlyApi();

                api.setModule(v1.getModule())
                        .setModuleName(v1.getModuleName())
                        .setOp(v1.getOp())
                        .setOpName(v1.getOpName()).setDescription(v1.getDescription()).setGrantAll(v1.isGrantAll()).setNeedAuthenticated(v1.isNeedAuthenticated()).setRequestUrl(v1.getRequestUrl());
                apis.put(k1, api);
            });
            module.setApis(apis);
            copy.put(k, module);
        });
        return copy;
    }

    /**
     * module+op
     * 获取不需要认证的接口 = 游客可见 + 所有认证后可见
     *
     * @return
     */
    public static List<String> ignorePermission() {
        return AuthenticationEndpointAnalysis.ignoreForAuth().stream().map(api -> PermissionStrUtils.permission(api.getModule(), api.getOp())).toList();
    }


    /**
     * apiInfos 是否包含该认证点API
     *
     * @param module
     * @param op
     * @return
     */
    public static boolean contains(String module, String op) {
        return allApis.stream().allMatch(api -> PermissionStrUtils.permission(api.getModule(), api.getOp()).equals(PermissionStrUtils.permission(module, op)));
    }

    /**
     * 判断认证点是否在系统内 入参是认证点 module+op
     */
    public static boolean contains(String moduleOp) {
        for (FlyApi api : allApis) {
            if (PermissionStrUtils.permission(api.getModule(), api.getOp()).equals(moduleOp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取不需要认证的接口 = 游客可见 + 所有认证后可见
     *
     * @return
     */
    public static Collection<FlyApi> ignoreForAuth() {
        return CollUtil.unmodifiable(CollUtil.union(whiteApis, grantAlleApis));
    }
}
