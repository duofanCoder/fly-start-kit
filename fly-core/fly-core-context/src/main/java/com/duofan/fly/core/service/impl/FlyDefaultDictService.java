package com.duofan.fly.core.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.duofan.fly.core.base.entity.FlyDict;
import com.duofan.fly.core.service.FlyDictService;
import com.duofan.fly.core.spi.DictExtension;
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
    List<DictExtension> dictExtensionList;

    @Override
    public List<FlyDict> list(String dicType) {

        DictExtension dic = dictExtensionList.stream()
                .filter(dictExtension -> StrUtil.equalsIgnoreCase(dicType, dictExtension.getType()))
                .findFirst()
                .orElse(null);

        if (ObjectUtil.isNotNull(dic)) {
            if (dic.isCacheable()) {
                // TODO 让字典缓存
            }
            return dic.list();
        } else {
            log.warn("字典类型[{}]不存在", dicType);
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
