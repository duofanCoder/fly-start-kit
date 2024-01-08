package com.duofan.fly.framework.security.constraint.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.entity.FlyRole;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.mapper.FlyRoleMapper;
import com.duofan.fly.core.mapper.FlyUserMapper;
import com.duofan.fly.framework.security.constraint.FlyLoginUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户查询
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/15
 */
@Slf4j
public class FlyUserDetailService implements UserDetailsService {
    private final FlyUserMapper userMapper;

    private final FlyRoleMapper roleMapper;

    public FlyUserDetailService(FlyUserMapper userMapper, FlyRoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FlyUser user = Optional.ofNullable(this.getByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误"));
        List<FlyResourceInfo> resources = roleMapper.loadRoleResourceByUsername(user.getUsername());

        List<FlyRole> roleList = resources.stream().map(r -> new FlyRole().setRoleNo(r.getRoleNo())
                .setRoleName(r.getRoleName())).distinct().collect(Collectors.toList());
        return new FlyLoginUser(user, roleList, resources);
    }

    public FlyUser getByUsername(String username) {
        LambdaQueryWrapper<FlyUser> wp = new LambdaQueryWrapper<FlyUser>();
        wp.eq(FlyUser::getUsername, username);
        return userMapper.selectOne(wp);
    }

}
