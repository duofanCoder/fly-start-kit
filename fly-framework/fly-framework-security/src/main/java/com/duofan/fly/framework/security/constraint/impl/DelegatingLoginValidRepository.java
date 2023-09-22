package com.duofan.fly.framework.security.constraint.impl;

import com.alibaba.fastjson2.JSONObject;
import com.duofan.fly.framework.security.constraint.FlyLoginValidRepository;
import com.duofan.fly.framework.security.exception.LoginValidException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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
public final class DelegatingLoginValidRepository implements FlyLoginValidRepository {

    @Resource
    private List<FlyLoginValidRepository> delegates;
//
//    public DelegatingLoginValidRepository(FlyLoginValidRepository... delegates) {
//        val rep = Arrays.stream(delegates).filter(i -> !i.getClass().equals(DelegatingLoginValidRepository.class)).toList();
////        this.delegates = rep.stream().sorted((o1, o2) -> o2.order() - o1.order()).toList();
//        this.delegates = rep;
//    }

    @Override
    public void doCheck(JSONObject data) throws LoginValidException {
        for (FlyLoginValidRepository delegate : this.delegates) {
            delegate.doCheck(data);
            log.info("登陆校验器-{},完成校验成功", delegate.getClass());
        }
    }


    @Override
    public int order() {
        return 0;
    }

}
