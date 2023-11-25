package com.duofan.fly.framework.security.constraint.impl;

import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import com.duofan.fly.framework.security.constraint.FlyLoginValidRepository;
import com.duofan.fly.framework.security.exception.LoginValidException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/25
 */
@Slf4j
public class FlyDefaultErrorCountRepository implements FlyLoginValidRepository {
    private final int maxErrorCount = 5;
    private final FlyCacheService cacheService;

    public FlyDefaultErrorCountRepository(FlyCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void doCheck(Map<String, Object> data) throws LoginValidException {
        String loginErrorCountKey = CacheKeyUtils.getLoginErrorCountKey(data.get("ip").toString(), data.get("username").toString());
        if (cacheService.getNum(loginErrorCountKey) > maxErrorCount) {
            throw new LoginValidException("密码错误次数过多，请明天再试");
        }
    }

    @Override
    public boolean supportError(LoginValidException e) {
        return false;
    }

    @Override
    public void errorHandle(Map<String, Object> data, LoginValidException e) {

    }

    @Override
    public void successHandle(Map<String, Object> data) {
        String loginErrorCountKey = CacheKeyUtils.getLoginErrorCountKey(data.get("ip").toString(), data.get("username").toString());
        cacheService.hasKeyThenDelete(loginErrorCountKey);
    }

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }
}
