package com.duofan.fly.core.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.duofan.fly.core.base.entity.FlyDict;
import com.duofan.fly.core.service.FlyDictService;
import com.duofan.fly.core.spi.DicExtension;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/17
 */
@Slf4j
@Service
public class FlyDefaultDictService implements FlyDictService {

    @Resource
    List<DicExtension> dicExtensionList;

    @Override
    public List<FlyDict> list(String dicType) {

        DicExtension dic = dicExtensionList.stream()
                .filter(dicExtension -> StrUtil.equalsIgnoreCase(dicType, dicExtension.getType()))
                .findFirst()
                .orElse(null);

        if (ObjectUtil.isNotNull(dic)) {
            if (dic.isCacheable()) {
                return null;
            } else {
                return dic.list();
            }
        }
        return null;
    }

    @Override
    public List<FlyDict> listBase(String dicType) {
        return null;
    }

    @Override
    public List<FlyDict> listTree(String dicType) {
        return null;
    }
}
