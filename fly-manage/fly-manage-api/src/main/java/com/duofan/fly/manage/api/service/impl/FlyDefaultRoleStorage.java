package com.duofan.fly.manage.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.AuthenticationEndpointAnalysis;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.entity.FlyRole;
import com.duofan.fly.core.base.entity.FlyRolePermission;
import com.duofan.fly.core.base.entity.FlyRoleRel;
import com.duofan.fly.core.domain.FlyApi;
import com.duofan.fly.core.domain.FlyModule;
import com.duofan.fly.core.dto.RoleDto;
import com.duofan.fly.core.mapper.FlyRoleMapper;
import com.duofan.fly.core.mapper.FlyRolePermissionMapper;
import com.duofan.fly.core.mapper.FlyRoleRelMapper;
import com.duofan.fly.core.storage.FlyRolePermissionStorage;
import com.duofan.fly.core.storage.FlyRoleStorage;
import com.duofan.fly.core.utils.PermissionStrUtils;
import com.duofan.fly.core.utils.QueryUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/23
 */
@Slf4j
@Component
public class FlyDefaultRoleStorage extends ServiceImpl<FlyRoleMapper, FlyRole> implements FlyRoleStorage {
    @Resource
    private FlyRoleMapper roleMapper;

    @Resource
    private FlyRoleRelMapper relMapper;

    @Resource
    private FlyRolePermissionMapper permissionMapper;

    @Resource
    private FlyRolePermissionStorage permissionStorage;

    @Override
    public List<FlyResourceInfo> loadRoleResource(String role) {
        return roleMapper.loadRoleResourceByUsername(role);
    }

    @Override
    public void addRoleRel(FlyRoleRel flyRoleRel) {
        QueryWrapper<FlyRoleRel> wp = QueryUtils.buildQueryWrapper(flyRoleRel, List.of("roleNo", "username", "rel"), FlyRoleRel.class);
        if (relMapper.exists(wp)) {
            return;
        }
        relMapper.insert(flyRoleRel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoleRel(List<FlyRoleRel> flyRoleRel) {
        flyRoleRel.forEach(this::addRoleRel);
    }

    @Override
    public void removeRoleRel(FlyRoleRel flyRoleRel) {
        QueryWrapper<FlyRoleRel> wp = QueryUtils.buildQueryWrapper(flyRoleRel, List.of("roleNo", "username", "rel"), FlyRoleRel.class);
        relMapper.delete(wp);
    }

    @Override
    public FlyPageInfo<FlyRole> page(FlyPageInfo<FlyRole> pageInfo, FlyRole role) {
        Page<FlyRole> page = QueryUtils.buildPage(pageInfo, FlyRole.class);
        QueryWrapper<FlyRole> wp = QueryUtils.buildQueryWrapper(role, List.of("roleNo", "isEnabled", "roleName"), FlyRole.class);
        Page<FlyRole> res = this.page(page, wp);
        return FlyPageInfo.of(res);
    }


    @Override
    public List<FlyModule> listOpsByRoleNo(String roleNo) {
        LambdaQueryWrapper<FlyRolePermission> wp = new LambdaQueryWrapper<>();
        wp.eq(FlyRolePermission::getRoleNo, roleNo);
        List<FlyRolePermission> roleOps = permissionMapper.selectList(wp);
        Map<String, FlyModule> ops = AuthenticationEndpointAnalysis.listOps();

        ArrayList<FlyModule> modules = CollUtil.newArrayList(ops.values());
        for (FlyRolePermission roleOp : roleOps) {
            for (FlyModule module : modules) {
                for (FlyApi api : module.getApis().values()) {
                    if (module.getModule().equals(roleOp.getModule()) && api.getOp().equals(roleOp.getOp())) {
                        api.setActivated(true);
                    }
                }
            }
        }
        return modules;
    }

    @Override
    public void remove(String roleNo) {
        QueryWrapper<FlyRole> wp = QueryUtils.buildQueryWrapper(new FlyRole().setRoleNo(roleNo), List.of("roleNo"), FlyRole.class);
        if (!roleMapper.exists(wp)) {
            return;
        }
        roleMapper.delete(wp);
    }

    @Override
    public void update(FlyRole role) {
        UpdateWrapper<FlyRole> wp = QueryUtils.buildUpdateWrapper(role, List.of("roleNo"), List.of("roleName", "isDisabled"), FlyRole.class);
        roleMapper.update(null, wp);
    }

    @Override
    public void bindPermission(FlyRolePermission permission) {
        QueryWrapper<FlyRolePermission> wp = QueryUtils.buildQueryWrapper(permission, List.of("op", "module", "roleNo"), FlyRolePermission.class);

        if (!permission.isActivated()) {
            permissionMapper.delete(wp);
        } else {
            try {
                permissionMapper.insert(permission);
            } catch (DuplicateKeyException e) {
                log.info(LogConstant.BUSINESS_OPERATION_LOG, "角色绑定", "重复绑定");
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(RoleDto role) {
        FlyRole entity = BeanUtil.copyProperties(role, FlyRole.class);
        UpdateWrapper<FlyRole> wp = QueryUtils.buildUpdateWrapper(entity, List.of("id", "roleNo"), List.of("isEnabled", "roleName", "remark"), FlyRole.class);
        this.update(entity, wp);
        this.permissionStorage.remove(new LambdaQueryWrapper<FlyRolePermission>().eq(FlyRolePermission::getRoleNo, role.getRoleNo()));
        Set<FlyRolePermission> permissions = role.getPermissions().stream()
                // 认证不需要授权的接口
                .filter(p -> !CollUtil.contains(AuthenticationEndpointAnalysis.ignorePermission(), p))
                // 只有需要授权的接口会写入到数据库
                .filter(AuthenticationEndpointAnalysis::contains).map(p -> new FlyRolePermission(role.getRoleNo(), PermissionStrUtils.module(p), PermissionStrUtils.operation(p))).collect(Collectors.toSet());
        this.permissionStorage.saveBatch(permissions);
    }

    @Override
    public void updateChangeEnabled(FlyRole flyRole) {
        UpdateWrapper<FlyRole> wp = QueryUtils.buildUpdateWrapper(flyRole, List.of("roleNo"), List.of("isEnabled"), FlyRole.class);
        roleMapper.update(null, wp);
    }

    @Override
    public List<String> getByUsername(String username) {
        QueryWrapper<FlyRoleRel> wp = QueryUtils.buildQueryWrapper(new FlyRoleRel().setUsername(username), List.of("username"), FlyRoleRel.class);
        List<FlyRoleRel> roleRels = relMapper.selectList(wp);
        if (CollUtil.isEmpty(roleRels)) {
            return null;
        }
        return roleRels.stream().map(FlyRoleRel::getRoleNo).collect(Collectors.toList());
    }

    /**
     * 删除用户角色关系
     *
     * @param entity
     */
    @Override
    public void removeRel(FlyRoleRel entity) {
        QueryWrapper<FlyRoleRel> wp = QueryUtils.buildQueryWrapper(entity, List.of("username", "roleNo", "rel"
        ), FlyRoleRel.class);
        relMapper.delete(wp);
    }
}
