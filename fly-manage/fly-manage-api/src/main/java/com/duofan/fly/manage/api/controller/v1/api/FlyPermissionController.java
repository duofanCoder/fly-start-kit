package com.duofan.fly.manage.api.controller.v1.api;

import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.storage.FlyRoleStorage;
import com.duofan.fly.manage.api.controller.v1.request.PermissionRequest;
import com.duofan.fly.manage.api.controller.v1.response.MenuResponse;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色权限控制
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/7
 */
@RestController
@RequestMapping("api/v1/permission")
@FlyAccessInfo(system = "FLY BOOT", moduleName = "菜单模块")
public class FlyPermissionController {

    @Resource
    private FlyRoleStorage roleStorage;


    /**
     * 获取所有接口权限，并根据当前用户标记是否拥有
     *
     * @param roleNo
     * @return
     */
    @PostMapping("/list")
    @FlyAccessInfo(opName = "查询所有接口信息", description = "所有模块接口，且标注当前角色是否拥有")
    FlyResult list(String roleNo) {
        return FlyResult.success(MenuResponse.MenuTree.of(roleStorage.listOpsByRoleNo(roleNo)));
    }

    @PostMapping("/bind")
    @FlyAccessInfo(opName = "绑定/解绑接口权限")
    FlyResult bind(@RequestBody @Valid PermissionRequest.PermissionBind request) {
        roleStorage.bindPermission(PermissionRequest.PermissionBind.of(request));
        return FlyResult.SUCCESS;
    }
    

}
