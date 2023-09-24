package com.duofan.fly.framework.security.constraint;

import com.alibaba.fastjson2.JSONObject;
import com.duofan.fly.core.base.domain.permission.FlyToken;

/**
 * jwt token 操作
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/24
 */
public interface FlyTokenService {

    FlyToken create(JSONObject payloads);

    JSONObject parse(String token);

    default JSONObject parse(String token, String prefix) {
        return null;
    }

}
