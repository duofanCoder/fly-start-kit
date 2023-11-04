package com.duofan.fly.manage.api.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.exception.FlyConstraintException;
import com.duofan.fly.core.base.entity.FlyDict;
import com.duofan.fly.core.mapper.FlyDictMapper;
import com.duofan.fly.core.service.FlyDictService;
import com.duofan.fly.core.storage.FlyDictStorage;
import com.duofan.fly.core.utils.QueryUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典管理
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/16
 */
@Slf4j
@Service
public class FlyDefaultDictStorage extends ServiceImpl<FlyDictMapper, FlyDict> implements FlyDictStorage {

    @Resource
    private FlyDictMapper dictMapper;

    @Resource
    private FlyDictService dictService;

    @Override
    public Map<String, List<FlyDict>> list(Set<String> typeList) {
        LinkedHashMap<String, List<FlyDict>> result = new LinkedHashMap<>();

        ArrayList<String> nonDynamicDictTypeList
                = new ArrayList<>();
        // TODO 动态字典数据安全
        // 动态字典
        typeList.forEach(type -> {
            List<FlyDict> list = dictService.list(type);
            if (list != null) {
                result.put(type, list);
            } else {
                nonDynamicDictTypeList.add(type);
            }
        });

        if (nonDynamicDictTypeList.isEmpty()) {
            return result;
        }
        // 非动态字典
        FlyDict dict = new FlyDict()
                .setType(StrUtil.join(",", nonDynamicDictTypeList));
        QueryWrapper<FlyDict> wp = QueryUtils.buildQueryWrapper(dict, List.of("type"), FlyDict.class);
        List<FlyDict> data = dictMapper.selectList(wp);
        Set<String> typeSet = data.stream().map(FlyDict::getType).collect(Collectors.toSet());

        for (String type : typeSet) {
            ArrayList<FlyDict> tmp = new ArrayList<>();
            for (FlyDict datum : data) {
                if (datum.getType().equals(type)) {
                    tmp.add(new FlyDict(datum.getType(), datum.getText(), datum.getCode()));
                }
            }
            result.put(type, tmp);
        }
        return result;
    }


    @Override
    public boolean save(FlyDict dict) {
        try {
            dictMapper.insert(dict);
        } catch (DuplicateKeyException e) {
            throw new FlyConstraintException("数据不符合，唯一约束");
        }
        return true;
    }

    @Override
    public void update(FlyDict dict) {
        UpdateWrapper<FlyDict> wp = QueryUtils.buildUpdateWrapper(dict, List.of("type", "code"), List.of("text", "sort", "remark"), FlyDict.class);
        try {
            dictMapper.update(dict, wp);
        } catch (DuplicateKeyException e) {
            throw new FlyConstraintException("数据不符合，唯一约束");
        }
    }

    @Override
    public FlyPageInfo<FlyDict> page(FlyPageInfo<FlyDict> pageInfo, FlyDict condition) {
        Page<FlyDict> page = QueryUtils.buildPage(pageInfo, FlyDict.class);
        QueryWrapper<FlyDict> wp = QueryUtils.buildQueryWrapper(condition, List.of("type", "name"), FlyDict.class);
        wp.orderByAsc("type", "sort");
        return FlyPageInfo.of(page(page, wp));
    }

    @Override
    public List<FlyDict> list(String type) {
        FlyDict dict = new FlyDict()
                .setType(type);
        QueryWrapper<FlyDict> wp = QueryUtils.buildQueryWrapper(dict, List.of("type"), FlyDict.class);
        wp.orderByAsc("sort");
        List<FlyDict> data = dictMapper.selectList(wp);
        if (CollUtil.isNotEmpty(data)) {
            return data;
        }
        return dictService.list(type);
    }

    @Override
    public void switchEnabled(FlyDict flyDict) {
        UpdateWrapper<FlyDict> wp = QueryUtils.buildUpdateWrapper(flyDict, List.of("type", "code"), List.of("isEnabled"), FlyDict.class);
        dictMapper.update(flyDict, wp);
    }

    @Override
    public Object listWrap(String type) {
        if (type.contains(",")) {
            String[] types = type.split(",");
            return list(Arrays.stream(types).collect(Collectors.toSet()));
        }
        return list(type);
    }
}
