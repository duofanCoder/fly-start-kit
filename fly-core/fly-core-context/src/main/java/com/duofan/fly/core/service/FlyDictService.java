package com.duofan.fly.core.service;


import com.duofan.fly.core.base.entity.FlyDict;

import java.util.List;

/**
 * 字典插件接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/17
 */

public interface FlyDictService {
    List<FlyDict> list(String dicType);

    List<FlyDict> listBase(String dicType);

    List<FlyDict> listTree(String dicType);
}
