package com.duofan.fly.core.base.domain.permission;

import com.duofan.fly.core.base.constant.security.SecurityConstant;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class FlyResourceInfo {
    private String roleName;
    private String roleNo;
    private String moduleName;
    private String module;
    private String op;
    private String opName;
    private String description;
    private boolean grantToAll;
    private String requestUrl;


    public FlyResourceInfo(FlyAccessInfo info) {
        this
                .setOp(info.op())
                .setOpName(info.opName())
                .setModule(info.module())
                .setModuleName(info.moduleName())
                .setGrantToAll(info.isGrantToAll());
    }

    // 获取operation 权限名
    public String getFullOp() {
        return String.format("%s%s.%s", SecurityConstant.OPERATION_PREFIX, module, op);
    }
}
