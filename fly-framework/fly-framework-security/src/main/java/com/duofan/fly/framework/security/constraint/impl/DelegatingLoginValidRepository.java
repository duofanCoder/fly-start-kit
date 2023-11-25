package com.duofan.fly.framework.security.constraint.impl;

import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import com.duofan.fly.framework.security.constraint.FlyLoginValidRepository;
import com.duofan.fly.framework.security.exception.LoginValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 代理登陆校验
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Slf4j
@Component
public class DelegatingLoginValidRepository {

    private final List<FlyLoginValidRepository> delegates;

    private final FlyCacheService cacheService;

    public DelegatingLoginValidRepository(FlyCacheService cacheService, FlyLoginValidRepository... delegates) {
        this.cacheService = cacheService;
        // delegates根据order 从小到大排序
        Arrays.sort(delegates, Comparator.comparingInt(FlyLoginValidRepository::order));
        this.delegates = List.of(delegates);
    }

    public void doCheck(Map<String, Object> data) throws LoginValidException {
        for (FlyLoginValidRepository delegate : this.delegates) {
            delegate.doCheck(data);
            log.info("登陆校验器-{},完成校验成功", delegate.getClass().getSimpleName());
        }
    }

    public void doErrHandle(Map<String, Object> data, Exception e) {
        if (e instanceof LoginValidException loginValidException) {
            this.delegates.stream().filter(d ->
                            d.supportError(loginValidException))
                    .forEach(i -> i.errorHandle(data, loginValidException));
        } else if (e instanceof AuthenticationException) {
            // 密码错误记录错误次数
            recordErrorCount(data.get("ip").toString(), data.get("username").toString());
        }
    }

    // 根据ip和用户账号 为key 记录错误次数
    // 如果错误次数大于5次，锁定账号
    private void recordErrorCount(String ip, String username) {
        cacheService.increment(CacheKeyUtils.getLoginErrorCountKey(ip, username), 1, 0, Duration.ofDays(1));
    }

    public void doSuccessHandle(Map<String, Object> data) {
        this.delegates.forEach(a -> a.successHandle(data));
    }
}
