package com.duofan.fly.core.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 角色传输类
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/18
 */
@Data
@Accessors(chain = true)
public class RoleDto {
    private String id;

    private String roleNo;
    private String roleName;
    private String isEnabled = "1";
    private List<String> permissions;
}
