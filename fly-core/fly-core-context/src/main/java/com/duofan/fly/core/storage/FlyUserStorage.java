package com.duofan.fly.core.storage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.domain.FlyUserDto;
import com.duofan.fly.core.dto.AdminLoginInfo;

/**
 * 用户管理接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
public interface FlyUserStorage extends IService<FlyUser> {
    FlyUser getByUsername(String username);

    void passwdReset(FlyUserDto userDto);

    AdminLoginInfo getLoginUserInfo();
}
