package com.duofan.fly.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 常见状态码
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Getter
@AllArgsConstructor
public enum FlyHttpStatus {
    SUCCESS("200", "操作成功", "通用成功"),
    FAIL("500", "系统繁忙，请稍后再试", "通用失败"),
    UNAUTHORIZED("401", "请先认证", "未登录操作"),
    NOT_FOUND("404", "未找到", "没找到数据"),
    FORBIDDEN("403", "无权访问", "没有操作权限");

    private final String code;
    private final String msg;
    private final String desc;
}
