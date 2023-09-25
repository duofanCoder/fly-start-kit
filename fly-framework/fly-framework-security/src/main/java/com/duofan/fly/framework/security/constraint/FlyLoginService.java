package com.duofan.fly.framework.security.constraint;


import com.duofan.fly.core.base.domain.permission.FlyToken;

import java.util.Map;

/**
 * 登陆操作接口约束
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
public interface FlyLoginService {
    FlyToken login(Map<String, Object> data);

}
