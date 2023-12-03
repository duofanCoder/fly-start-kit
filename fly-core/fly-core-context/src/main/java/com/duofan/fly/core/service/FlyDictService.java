package com.duofan.fly.core.service;


import com.duofan.fly.core.base.domain.common.FlyDictionary;
import com.duofan.fly.core.base.entity.FlyDictData;

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
    List<FlyDictionary> list(String dicType);

    List<FlyDictionary> listBase(String dicType);

    List<FlyDictionary> listTree(String dicType);
}
