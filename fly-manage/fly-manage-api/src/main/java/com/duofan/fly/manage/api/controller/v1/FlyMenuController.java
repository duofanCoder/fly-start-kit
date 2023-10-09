package com.duofan.fly.manage.api.controller.v1;

import com.duofan.fly.core.AuthenticationEndpointAnalysis;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.storage.FlyRoleStorage;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单权限控制
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/7
 */
@RestController
@RequestMapping("api/v1/operation")
@FlyAccessInfo(system = "FLY BOOT", moduleName = "菜单模块")
public class FlyMenuController {

    @Resource
    private AuthenticationEndpointAnalysis analysis;

    @Resource
    private FlyRoleStorage roleStorage;


    @PostMapping("/listOps")
    @FlyAccessInfo(opName = "列出接口")
    FlyResult listOps(String roleNo) {
        return FlyResult.success(roleStorage.listOpsByRoleNo(roleNo));
    }


    @PostMapping("")
    @FlyAccessInfo(opName = "")
    FlyResult addOps() {
        return FlyResult.SUCCESS;
    }
}
