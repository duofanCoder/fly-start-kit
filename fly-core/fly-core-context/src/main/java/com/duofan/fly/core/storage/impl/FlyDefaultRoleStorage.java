package com.duofan.fly.core.storage.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.entity.FlyRole;
import com.duofan.fly.core.base.entity.FlyRoleRel;
import com.duofan.fly.core.mapper.FlyRoleMapper;
import com.duofan.fly.core.mapper.FlyRoleRelMapper;
import com.duofan.fly.core.storage.FlyRoleStorage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public List<FlyResourceInfo> loadRoleResource(String role) {
        return roleMapper.loadRoleResource(role);
    }

    @Override
    public void addRoleRel(FlyRoleRel flyRoleRel) {
        relMapper.insert(flyRoleRel);
    }

}
