package com.duofan.fly.core.utils;

import cn.hutool.core.util.StrUtil;

/**
 * 权限字符串操作
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/16
 */
public class PermissionStrUtils {

    public static String module(String permission) {
        return StrUtil.subPre(permission, permission.lastIndexOf("."));
    }

    public static String operation(String permission) {
        return StrUtil.subSuf(permission, permission.lastIndexOf(".") + 1);
    }

    public static String permission(String getModule, String getOp) {
        return StrUtil.format("{}.{}", getModule, getOp);
    }
}
