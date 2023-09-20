package com.duofan.fly.core.base.domain.permission;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 接口资源定义
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Setter
@Getter
@Accessors(chain = true)
public class FlyResourceInfo {
    private String roleName;
    private String roleNo;
    private String moduleName;
    private String module;
    private String op;
    private String opName;
    private String description;
    private String grantToAll;
}
