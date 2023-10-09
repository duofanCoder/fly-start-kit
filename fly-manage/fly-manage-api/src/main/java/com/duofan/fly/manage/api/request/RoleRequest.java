package com.duofan.fly.manage.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

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
    @NotBlank(message = "角色名不能为空")
    private String roleNo;
    @NotBlank(message = "角色名称不能为空")
    @Range(min = 3, max = 16, message = "角色名词至少3-16个字符")
    private String roleName;
    private String isDisabled = "1";
}
