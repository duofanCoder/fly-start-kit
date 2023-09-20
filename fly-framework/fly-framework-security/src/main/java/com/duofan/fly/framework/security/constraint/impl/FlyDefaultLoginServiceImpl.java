package com.duofan.fly.framework.security.constraint.impl;

import com.alibaba.fastjson2.JSONObject;
import com.duofan.fly.framework.security.constraint.AbstractLoginService;
import org.springframework.stereotype.Component;

/**
 * 默认登陆实现
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Component
public class FlyDefaultLoginServiceImpl extends AbstractLoginService {

    
    @Override
    public JSONObject login(JSONObject data) {
        return super.login(data);
    }
}
