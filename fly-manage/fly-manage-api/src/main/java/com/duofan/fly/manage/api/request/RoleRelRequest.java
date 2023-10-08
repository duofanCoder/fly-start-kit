package com.duofan.fly.manage.api.request;

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
    private String username;
    private String roleNo;
    private String rel;
}
