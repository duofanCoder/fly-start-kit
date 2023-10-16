package com.duofan.fly.manage.api.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.entity.FlyDict;
import com.duofan.fly.core.mapper.FlyDictMapper;
import com.duofan.fly.core.storage.FlyDictStorage;
import com.duofan.fly.core.utils.QueryUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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

    @Override
    public List<FlyDict> list(List<String> typeList) {
        FlyDict dict = new FlyDict()
                .setType(StrUtil.join(",", typeList));
        QueryWrapper<FlyDict> wp = QueryUtils.buildQueryWrapper(dict, List.of("type"), FlyDict.class);
        List<FlyDict> data = dictMapper.selectList(wp);
        Set<String> typeSet = data.stream().map(FlyDict::getType).collect(Collectors.toSet());
        LinkedList<FlyDict> result = new LinkedList<>();
        for (String type : typeSet) {
            for (FlyDict datum : data) {
                if (datum.getType().equals(type)) {
                    result.add(new FlyDict().setCode(datum.getCode()).setText(datum.getText()).setType(datum.getType()));
                }
            }
        }
        return result;
    }

    @Override
    public boolean save(FlyDict dict) {
        try {
            dictMapper.insert(dict);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("数据不符合，唯一约束");
        }
        return true;
    }

    @Override
    public void update(FlyDict dict) {
        UpdateWrapper<FlyDict> wp = QueryUtils.buildUpdateWrapper(dict, List.of("type", "code"), List.of("text", "sort", "remark"), FlyDict.class);
        try {
            dictMapper.update(dict, wp);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("数据不符合，唯一约束");
        }
    }

    @Override
    public FlyPageInfo<FlyDict> page(FlyPageInfo<FlyDict> pageInfo, FlyDict condition) {
        Page<FlyDict> page = QueryUtils.buildPage(pageInfo, FlyDict.class);
        QueryWrapper<FlyDict> wp = QueryUtils.buildQueryWrapper(condition, List.of("username", "email", "phone", "isLocked", "isEnabled"), FlyDict.class);
        return FlyPageInfo.of(page(page, wp));
    }
}
