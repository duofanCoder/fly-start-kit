package com.duofan.fly.manage.api.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.exception.FlyConstraintException;
import com.duofan.fly.core.base.entity.FlyDictData;
import com.duofan.fly.core.mapper.FlyDictDataMapper;
import com.duofan.fly.core.storage.FlyDictDataStorage;
import com.duofan.fly.core.utils.QueryUtils;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author duofan
 * @since 2023-12-03
 */
@Service
public class FlyDefaultDictDataStorage extends ServiceImpl<FlyDictDataMapper, FlyDictData> implements FlyDictDataStorage {
    @Resource
    private FlyDictDataMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(FlyDictData entity) {
        entity.setId(null);
        duplicateCheck(entity);

        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(FlyDictData entity) {
        duplicateCheck(entity);
        return updateById(entity);
    }

    private void duplicateCheck(FlyDictData entity) {
        FlyDictData dictData = new FlyDictData();
        dictData.setType(entity.getType());
        dictData.setValue(entity.getValue());
        QueryWrapper<FlyDictData> wp = QueryUtils.buildQueryWrapper(dictData, List.of("type","value"), FlyDictData.class);
        wp.ne(StrUtil.isNotBlank(entity.getId()),"id", entity.getId());
        if (mapper.selectCount(wp) > 0) {
            throw new FlyConstraintException("字典数据已存在");
        }
    }

    @Override
    public FlyPageInfo<FlyDictData> page(FlyPageInfo<FlyDictData> pageInfo, FlyDictData user) {
        Page<FlyDictData> page = QueryUtils.buildPage(pageInfo, FlyDictData.class);
        QueryWrapper<FlyDictData> wp = QueryUtils.buildQueryWrapper(user, List.of("type","label","value","isEnabled"), FlyDictData.class);
        Page<FlyDictData> data = page(page, wp);
        wp.orderByDesc("update_time");
        return FlyPageInfo.of(data);
    }


    @Override
    public boolean switchStatus(String id, String isEnabled) {
        FlyDictData model = new FlyDictData();
        model
                .setIsEnabled(isEnabled)
                .setId(id);
        return this.updateById(model);
    }
}
