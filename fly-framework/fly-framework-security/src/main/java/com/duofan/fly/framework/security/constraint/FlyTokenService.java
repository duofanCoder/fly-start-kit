package com.duofan.fly.framework.security.constraint;

import com.duofan.fly.core.base.domain.permission.FlyToken;

import java.util.Map;

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

    FlyToken create(FlyLoginUser loginUser);

    Map<String, Object> parse(String token);

    default Map<String, Object> parse(String token, String prefix) {
        return null;
    }

    /**
     * 校验TOKEN是否过期
     *
     * @param token
     * @return
     */
    boolean validate(String token);

    /**
     * 校验TOKEN是否被篡改
     *
     * @param token
     * @return
     */
    boolean verify(String token);

    void refresh(String token);
}
