package com.duofan.fly.framework.security.constraint.impl;

import com.duofan.fly.framework.security.constraint.FlyLoginValidRepository;
import com.duofan.fly.framework.security.exception.LoginValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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

    public DelegatingLoginValidRepository(FlyLoginValidRepository... delegates) {
        this.delegates = Arrays.stream(delegates).sorted((o1, o2) -> o2.order() - o1.order()).toList();
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
        }
    }

    public void doSuccessHandle(Map<String, Object> data) {
        this.delegates.forEach(a -> a.successHandle(data));
    }
}
