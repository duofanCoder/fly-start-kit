package com.duofan.fly.core.base.constant.security;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 访问敏感接口资源约束单位
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/5
 */
@Getter
@AllArgsConstructor
public enum AccessSensitiveResourceConstrainedTarget {
    // 通过IP地址 识别用户
    LIMIT_IP("0", "限制IP", "限制IP"),
    // 通过浏览器标识 识别用户
    LIMIT_USER("1", "限制用户", "限制用户"),
    // 限制接口
    LIMIT_API("2", "限制接口", "限制接口");


    private final String code;
    private final String msg;
    private final String desc;
}
