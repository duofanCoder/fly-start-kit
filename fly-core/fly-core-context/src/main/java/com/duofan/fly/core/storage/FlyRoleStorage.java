package com.duofan.fly.core.storage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.entity.FlyRole;
import com.duofan.fly.core.base.entity.FlyRolePermission;
import com.duofan.fly.core.base.entity.FlyRoleRel;
import com.duofan.fly.core.domain.FlyModule;
import com.duofan.fly.core.dto.RoleDto;
import org.springframework.transaction.annotation.Transactional;

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

    void addRoleRel(List<FlyRoleRel> flyRoleRel);

    void removeRoleRel(FlyRoleRel flyRoleRel);

    FlyPageInfo<FlyRole> page(FlyPageInfo<FlyRole> pageInfo, FlyRole role);

    List<FlyModule> listOpsByRoleNo(String roleNo);

    void remove(String roleNo);

    void update(FlyRole role);

    void bindPermission(FlyRolePermission permission);

    @Transactional(rollbackFor = Exception.class)
    void saveOrUpdate(RoleDto role);

    void updateChangeEnabled(FlyRole flyRole);

    List<String> getByUsername(String username);

    void removeRel(FlyRoleRel entity);
}
