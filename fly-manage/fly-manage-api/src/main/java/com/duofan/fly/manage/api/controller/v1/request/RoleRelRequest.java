package com.duofan.fly.manage.api.controller.v1.request;

import com.duofan.fly.manage.api.dict.RoleDict;
import com.duofan.fly.validate.constraint.Dict;
import com.duofan.fly.validate.constraint.IdCardNo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户角色绑定关系
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/8
 */
@Data
public class RoleRelRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @Dict(dict = "roleDict", message = "角色不存在")
    private String roleNo;
    @IdCardNo
    private String rel;
}
