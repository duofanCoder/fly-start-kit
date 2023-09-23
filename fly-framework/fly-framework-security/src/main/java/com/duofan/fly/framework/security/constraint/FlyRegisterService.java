package com.duofan.fly.framework.security.constraint;

import com.duofan.fly.core.base.entity.FlyUser;

/**
 * 注册用户服务
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/23
 */
public interface FlyRegisterService {

    void register(FlyUser data);
}
