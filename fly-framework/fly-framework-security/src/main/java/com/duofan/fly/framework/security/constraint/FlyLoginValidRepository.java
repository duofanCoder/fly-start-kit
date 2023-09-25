package com.duofan.fly.framework.security.constraint;

import com.duofan.fly.framework.security.exception.LoginValidException;

import java.util.Map;

/**
 * 自定义登陆校验接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
public interface FlyLoginValidRepository {

    void doCheck(Map<String, Object> data) throws LoginValidException;

    int order();
}
