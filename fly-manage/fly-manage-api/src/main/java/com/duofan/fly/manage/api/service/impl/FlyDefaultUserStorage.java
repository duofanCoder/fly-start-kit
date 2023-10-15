package com.duofan.fly.manage.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.exception.FlyException;
import com.duofan.fly.core.base.domain.permission.FlyRoleEnums;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.dto.AdminLoginInfo;
import com.duofan.fly.core.dto.UserDto;
import com.duofan.fly.core.mapper.FlyUserMapper;
import com.duofan.fly.core.storage.FlyUserStorage;
import com.duofan.fly.core.utils.QueryUtils;
import com.duofan.fly.framework.security.constraint.FlyLoginUser;
import com.duofan.fly.framework.security.context.FlySessionHolder;
import com.duofan.fly.framework.security.exception.FlySuspiciousSecurityException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Slf4j
@Component
public class FlyDefaultUserStorage extends ServiceImpl<FlyUserMapper, FlyUser> implements FlyUserStorage {

    @Resource
    private FlyUserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public FlyUser getByUsername(String username) {
        LambdaQueryWrapper<FlyUser> wp = new LambdaQueryWrapper<FlyUser>();
        wp.eq(FlyUser::getUsername, username);
        return userMapper.selectOne(wp);
    }

    @Override
    public void passwdReset(UserDto userDto) {
        if (!FlySessionHolder.hasRole(FlyRoleEnums.ADMIN.getRoleNo()) &&
                !FlySessionHolder.currentUsername().equals(userDto.getUsername())) {
            log.info(LogConstant.SUSPICIOUS_OPERATION_LOG + "，被修改用户名：{}", "重置密码", "越权修改其他用户密码", userDto.getUsername());
            throw new FlySuspiciousSecurityException("密码修改失败，请稍后再试");
        }

        FlyUser user = getByUsername(userDto.getUsername());
        String rawEncoderPasswd = passwordEncoder.encode(userDto.getRawPassword());
        String newEncoderPasswd = passwordEncoder.encode(userDto.getNewPassword());
        if (!user.getPassword().equals(rawEncoderPasswd)) {
            throw new FlyException("修改失败，原密码错误");
        }
        FlyUser flyUser = new FlyUser().setUsername(userDto.getUsername())
                .setPassword(newEncoderPasswd);
        flyUser.setId(user.getId());
        this.updateById(flyUser);
    }

    @Override
    public AdminLoginInfo getLoginUserInfo() {
        FlyLoginUser login = FlySessionHolder.currentUser();
        AdminLoginInfo info = new AdminLoginInfo();
        info.setRole(login.getRoleList().get(0))
                .setRoleList(login.getRoleList())
                .setUsername(login.getUsername())
                .setAuthorityList(login.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
        return info;
    }

    @Override
    public FlyPageInfo<FlyUser> page(FlyPageInfo<FlyUser> pageInfo, FlyUser user) {
        Page<FlyUser> page = QueryUtils.buildPage(pageInfo, FlyUser.class);
        QueryWrapper<FlyUser> wp = QueryUtils.buildQueryWrapper(user, List.of("username", "email", "phone", "", "isLocked", "isEnabled"), FlyUser.class);
        return FlyPageInfo.of(page(page, wp));
    }

    @Override
    public FlyPageInfo<UserDto> page(FlyPageInfo<FlyUser> pageInfo, UserDto condition) {
        
        return null;
    }


}
