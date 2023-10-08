package com.duofan.fly.manage.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
    @NotBlank
    private String roleNo;
    @NotBlank
    private String roleName;
    private String isDisabled;
}
