package com.duofan.fly.manage.api.controller.v1.request;

import com.duofan.fly.validate.constraint.Dict;
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
    public static class SaveOrUpdate {
        private String id;
        @NotBlank(message = "角色名不能为空")
        @Length(min = 3, max = 16, message = "角色名称至少3-16个字符")
        private String roleNo;
        @NotBlank(message = "角色名称不能为空")
        @Length(min = 2, max = 16, message = "角色名称至少3-16个字符")
        private String roleName;
        @Dict(dict = "booleanDict", message = "字典类型不存在")
        private String isEnabled = "1";
        private List<String> permissions;
    }


    // 状态修改开启关闭
    @Data
    public static class Status {
        @NotBlank(message = "角色编号不能为空")
        private String roleNo;
        @NotBlank(message = "状态不能为空")
        private String isEnabled;
    }

}
