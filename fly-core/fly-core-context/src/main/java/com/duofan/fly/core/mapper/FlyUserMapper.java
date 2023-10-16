package com.duofan.fly.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.dto.UserDto;

/**
 * user
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/13
 */
public interface FlyUserMapper extends BaseMapper<FlyUser> {
    IPage<UserDto> page(IPage<?> page, UserDto user);
}
