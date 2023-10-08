package com.duofan.fly.manage.api.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.entity.FlyRole;
import com.duofan.fly.core.base.entity.FlyRoleOp;
import com.duofan.fly.core.base.entity.FlyRoleRel;
import com.duofan.fly.core.domain.FlyModule;
import com.duofan.fly.core.mapper.FlyRoleMapper;
import com.duofan.fly.core.mapper.FlyRoleOpMapper;
import com.duofan.fly.core.mapper.FlyRoleRelMapper;
import com.duofan.fly.core.storage.FlyRoleStorage;
import com.duofan.fly.framework.security.config.AuthenticationEndpointAnalysis;
import com.duofan.fly.manage.api.utils.QueryUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
    private FlyRoleOpMapper roleOpMapper;

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
        LambdaQueryWrapper<FlyRoleOp> wp = new LambdaQueryWrapper<>();
        wp.eq(FlyRoleOp::getRoleNo, roleNo);
        List<FlyRoleOp> roleOps = roleOpMapper.selectList(wp);
        Map<String, FlyModule> ops = AuthenticationEndpointAnalysis.listOps();

        ArrayList<FlyModule> modules = CollUtil.newArrayList(ops.values());
        for (FlyModule module : modules) {
            Set<String> opSet = roleOps.stream()
                    .filter(r -> module.getModule().equals(r.getModule()))
                    .map(FlyRoleOp::getOp)
                    .collect(Collectors.toSet());
            module.getApis().values().stream()
                    .filter(api -> CollUtil.contains(opSet, api.getOp()))
                    .forEach(i -> i.setOwn(true));
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
        UpdateWrapper<FlyRole> wp = QueryUtils.buildUpdateWrapper(role, List.of("roleNo"),
                List.of("roleName", "isDisabled"), FlyRole.class);
        roleMapper.update(null, wp);
    }

}
