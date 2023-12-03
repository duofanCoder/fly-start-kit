package com.duofan.fly.manage.api.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.duofan.fly.core.base.domain.common.FlyDictionary;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.exception.FlyConstraintException;
import com.duofan.fly.core.base.entity.FlyDictData;
import com.duofan.fly.core.base.entity.FlyDictType;
import com.duofan.fly.core.mapper.FlyDictTypeMapper;
import com.duofan.fly.core.service.FlyDictService;
import com.duofan.fly.core.storage.FlyDictDataStorage;
import com.duofan.fly.core.storage.FlyDictTypeStorage;
import com.duofan.fly.core.utils.QueryUtils;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author duofan
 * @since 2023-12-03
 */
@Service
public class FlyDefaultDictTypeStorage extends ServiceImpl<FlyDictTypeMapper, FlyDictType> implements FlyDictTypeStorage {
    @Resource
    private FlyDictTypeMapper mapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean save(FlyDictType entity){
        entity.setId(null);
        // 查重type
        duplicateCheck(entity);
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean edit(FlyDictType entity){
        // 查重type
        duplicateCheck(entity);
        return updateById(entity);
    }

    private void duplicateCheck(FlyDictType entity) {
        FlyDictType dictType = new FlyDictType();
        dictType.setType(entity.getType());
        QueryWrapper<FlyDictType> wp = QueryUtils.buildQueryWrapper(dictType, List.of("type"), FlyDictType.class);
        if (mapper.selectCount(wp) > 0) {
            throw new FlyConstraintException("字典类型已存在");
        }
    }

    @Override
    public FlyPageInfo<FlyDictType>page(FlyPageInfo<FlyDictType>pageInfo,FlyDictType user){
        Page<FlyDictType>page=QueryUtils.buildPage(pageInfo,FlyDictType.class);
        QueryWrapper<FlyDictType>wp=QueryUtils.buildQueryWrapper(user, List.of("createTime"),FlyDictType.class);
        Page<FlyDictType>data=page(page,wp);
        wp.orderByDesc("update_time");
        return FlyPageInfo.of(data);
    }


    @Override
    public boolean switchStatus(String id, String status) {
        FlyDictType model = new FlyDictType();
        model
        //.setStatus(status)
        .setId(id);
        return this.updateById(model);
    }
    @Resource
    FlyDictDataStorage dictDataStorage;

    @Resource
    private FlyDictService dictService;

    @Override
    public Map<String, List<FlyDictionary>> list(Set<String> typeList) {
        LinkedHashMap<String, List<FlyDictionary>> result = new LinkedHashMap<>();

        ArrayList<String> nonDynamicDictTypeList
                = new ArrayList<>();
        // TODO 动态字典数据安全
        // 动态字典
        typeList.forEach(type -> {
            List<FlyDictionary> list = dictService.list(type);
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
        FlyDictData dict = new FlyDictData()
                .setType(StrUtil.join(",", nonDynamicDictTypeList));
        QueryWrapper<FlyDictData> wp = QueryUtils.buildQueryWrapper(dict, List.of("type"), FlyDictData.class);
        List<FlyDictData> data = dictDataStorage.list(wp);
        Set<String> typeSet = data.stream().map(FlyDictData::getType).collect(Collectors.toSet());

        for (String type : typeSet) {
            ArrayList<FlyDictionary> tmp = new ArrayList<>();
            for (FlyDictData datum : data) {
                if (datum.getType().equals(type)) {
                    tmp.add(new FlyDictionary(datum.getType(), datum.getLabel(), datum.getValue()));
                }
            }
            result.put(type, tmp);
        }
        return result;
    }
    public List<FlyDictionary> list(String type) {
        FlyDictData dict = new FlyDictData()
                .setType(type);
        QueryWrapper<FlyDictData> wp = QueryUtils.buildQueryWrapper(dict, List.of("type"), FlyDictData.class);
        wp.orderByAsc("sort");
        List<FlyDictionary> data = dictDataStorage.list(wp).stream().map(i -> new FlyDictionary(i.getType(), i.getLabel(), i.getValue())).toList();
        if (CollUtil.isNotEmpty(data)) {
            return data;
        }
        return dictService.list(type);
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
