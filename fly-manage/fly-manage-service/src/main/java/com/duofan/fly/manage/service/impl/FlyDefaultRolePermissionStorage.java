package com.duofan.fly.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.entity.FlyRolePermission;
import com.duofan.fly.core.mapper.FlyRolePermissionMapper;
import com.duofan.fly.core.storage.FlyRolePermissionStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/18
 */
@Slf4j
@Service
public class FlyDefaultRolePermissionStorage extends ServiceImpl<FlyRolePermissionMapper, FlyRolePermission> implements FlyRolePermissionStorage {

}
