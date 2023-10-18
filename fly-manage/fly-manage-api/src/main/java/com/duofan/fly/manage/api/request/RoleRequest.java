package com.duofan.fly.manage.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 角色操作请求
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/8
 */
@Data
public class RoleRequest {
    @Data
    public static class Save {
        @NotBlank(message = "角色名不能为空")
        @Length(min = 3, max = 16, message = "角色名称至少3-16个字符")
        private String roleNo;
        @NotBlank(message = "角色名称不能为空")
        @Length(min = 2, max = 16, message = "角色名称至少3-16个字符")
        private String roleName;
        private String isEnabled = "1";
    }

    @Data
    public static class Update {

    }

    @Data
    public static class UpdateAndPermission {
        @NotBlank(message = "角色名不能为空")
        @Length(min = 3, max = 16, message = "角色名称至少3-16个字符")
        private String roleNo;
        @NotBlank(message = "角色名称不能为空")
        @Length(min = 2, max = 16, message = "角色名称至少3-16个字符")
        private String roleName;
        private String isEnabled = "1";

        @Valid
        private List<Permission> permissions;
    }

    /**
     * 所有选中绑定的权限
     */
    @Data
    public static class Permission {
        @NotBlank(message = "权限表示不能为空")
        private String value;
        @NotBlank(message = "角色名不能为空")
        private String roleNo;
    }

}
