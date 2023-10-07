package com.duofan.fly.core.base.domain.permission.access;

import java.lang.annotation.*;

/**
 * 起飞Controller访问核心注解
 * 日志、安全统一
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/13
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface FlyAccessInfo {
    String system() default "";

    // 默认就是权限定类名
    String module() default "";

    String moduleName() default "";

    // 默认就是方法名
    String op() default "";

    String opName() default "";

    // 是否启动权限校验
    boolean isValid() default true;

    boolean isGrantToAll() default false;

    String description() default "";
}
