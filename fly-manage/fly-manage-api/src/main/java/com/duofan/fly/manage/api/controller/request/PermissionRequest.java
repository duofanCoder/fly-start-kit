package com.duofan.fly.manage.api.controller.request;

import com.duofan.fly.core.base.entity.FlyRolePermission;
import com.duofan.fly.core.utils.PermissionStrUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/16
 */
public class PermissionRequest {

    @Data
    @Accessors(chain = true)
    public static class PermissionBind {
        @NotBlank(message = "角色号不能为空")
        @NotNull
        private String roleNo;
        @NotNull
        @NotBlank(message = "权限不能为空")
        private String permission;
        private boolean isBind;

        public static FlyRolePermission of(PermissionBind bind) {
            return new FlyRolePermission().setActivated(bind.isBind())
                    .setOp(PermissionStrUtils.module(bind.getPermission()))
                    .setModule(PermissionStrUtils.operation(bind.getPermission()));
        }
    }
}
