package com.duofan.fly.core.storage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.entity.FlyRole;
import com.duofan.fly.core.base.entity.FlyRoleRel;

import java.util.List;

/**
 * 角色管理接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
public interface FlyRoleStorage extends IService<FlyRole> {

    List<FlyResourceInfo> loadRoleResource(String username);

    void addRoleRel(FlyRoleRel flyRoleRel);
}
