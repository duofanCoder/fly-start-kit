package com.duofan.fly.core.storage.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.mapper.FlyUserMapper;
import com.duofan.fly.core.storage.FlyUserStorage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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

    @Resource(type = FlyUserMapper.class)
    private FlyUserMapper userMapper;

    @Override
    public FlyUser selectByUsername(String username) {
        LambdaQueryWrapper<FlyUser> wp = new LambdaQueryWrapper<FlyUser>();
        wp.eq(FlyUser::getUsername, username);
        return userMapper.selectOne(wp);
    }
}
