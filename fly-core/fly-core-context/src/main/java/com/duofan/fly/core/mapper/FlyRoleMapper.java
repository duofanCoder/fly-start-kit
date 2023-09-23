package com.duofan.fly.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.entity.FlyRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色mapper
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/13
 */
public interface FlyRoleMapper extends BaseMapper<FlyRole> {

    List<FlyResourceInfo> loadRoleResource(@Param("username") String username);

}
