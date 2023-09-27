package com.duofan.fly.manage.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.domain.exception.FlyException;
import com.duofan.fly.core.base.domain.permission.FlyRoleEnums;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.constant.log.LogConstant;
import com.duofan.fly.core.domain.FlyUserDto;
import com.duofan.fly.core.mapper.FlyUserMapper;
import com.duofan.fly.core.storage.FlyUserStorage;
import com.duofan.fly.framework.security.context.FlySecurityContextHolder;
import com.duofan.fly.framework.security.exception.FlySuspiciousSecurityException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
    public void passwdReset(FlyUserDto userDto) {
        if (!FlySecurityContextHolder.hasRole(FlyRoleEnums.ADMIN.getRoleNo()) &&
                !FlySecurityContextHolder.currentUsername().equals(userDto.getUsername())) {
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


}
