package com.duofan.fly.core.base.domain.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色常量
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/23
 */
@Getter
@AllArgsConstructor
public enum FlyRoleEnums {

    DEFAULT("default", "默认"),
    ADMIN("admin", "管理员");

    private final String roleNo;
    private final String roleName;
}
