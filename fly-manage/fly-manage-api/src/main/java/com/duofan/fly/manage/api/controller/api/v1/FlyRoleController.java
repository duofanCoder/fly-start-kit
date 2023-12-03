package com.duofan.fly.manage.api.controller.api.v1;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.cglib.CglibUtil;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.base.entity.FlyRole;
import com.duofan.fly.core.base.entity.FlyRoleRel;
import com.duofan.fly.core.dto.RoleDto;
import com.duofan.fly.core.storage.FlyRoleStorage;
import com.duofan.fly.manage.api.controller.request.RoleRelRequest;
import com.duofan.fly.manage.api.controller.request.RoleRequest;
import com.duofan.fly.validate.plugins.ValidationList;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/8
 */
@RestController
@RequestMapping("/api/v1/role")
@FlyAccessInfo(system = "FLY BOOT", moduleName = "角色模块")
public class FlyRoleController {

    @Resource
    private FlyRoleStorage roleStorage;


    /**
     * 分页角色
     *
     * @param pageInfo 分页信息
     * @param role     角色信息
     * @return 分页角色
     */
    @PostMapping("/page")
    @FlyAccessInfo(opName = "分页角色")
    FlyResult page(@RequestBody(required = false) FlyPageInfo<FlyRole> pageInfo, @RequestBody(required = false) FlyRole role) {
        return FlyResult.success(roleStorage.page(pageInfo, role));
    }

    @PostMapping("/create")
    @FlyAccessInfo(opName = "添加角色")
    FlyResult create(@RequestBody @Validated RoleRequest.SaveOrUpdate request) {
        roleStorage.saveOrUpdate(BeanUtil.copyProperties(request, RoleDto.class));
        return FlyResult.SUCCESS;
    }

    @PostMapping("/update")
    @FlyAccessInfo(opName = "修改角色")
    FlyResult update(@RequestBody @Validated RoleRequest.SaveOrUpdate request) {
        roleStorage.saveOrUpdate(BeanUtil.copyProperties(request, RoleDto.class));
        return FlyResult.SUCCESS;
    }

    @PostMapping("/remove")
    @FlyAccessInfo(opName = "删除角色")
    FlyResult remove(@RequestBody String roleNo) {
        roleStorage.remove(roleNo);
        return FlyResult.SUCCESS;
    }


    @PostMapping("/bind/create")
    @FlyAccessInfo(opName = "角色绑定用户")
    FlyResult addRoleRel(@RequestBody @Valid ValidationList<RoleRelRequest> roleRels) {
        roleStorage.addRoleRel(BeanUtil.copyToList(roleRels, FlyRoleRel.class));
        return FlyResult.SUCCESS;
    }

    @PostMapping("/bind/remove")
    @FlyAccessInfo(opName = "角色解绑用户")
    FlyResult removeRoleRel(@RequestBody List<RoleRelRequest> roleRels) {
        CglibUtil.copyList(roleRels, FlyRoleRel::new).forEach(r -> roleStorage.removeRoleRel(r));
        return FlyResult.SUCCESS;
    }


    // 角色状态开启/关闭
    @PostMapping("/toggle/enabled")
    @FlyAccessInfo(opName = "角色状态开启/关闭")
    FlyResult status(@RequestBody RoleRequest.Status status) {
        roleStorage.updateChangeEnabled(BeanUtil.copyProperties(status, FlyRole.class));
        return FlyResult.SUCCESS;
    }
}
