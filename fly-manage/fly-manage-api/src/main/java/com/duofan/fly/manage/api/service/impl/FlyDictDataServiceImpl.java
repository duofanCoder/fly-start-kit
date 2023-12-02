package com.duofan.fly.manage.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.entity.FlyDictData;
import com.duofan.fly.core.mapper.FlyDictDataMapper;
import com.duofan.fly.core.storage.FlyDictDataService;
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
public class FlyDictDataServiceImpl extends ServiceImpl<FlyDictDataMapper, FlyDictData> implements FlyDictDataService {
    @Resource
    private FlyDictDataMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(FlyDictData entity) {
        entity.setId(null);
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(FlyDictData entity) {
        return updateById(entity);
    }

    @Override
    public FlyPageInfo<FlyDictData> page(FlyPageInfo<FlyDictData> pageInfo, FlyDictData user) {
        Page<FlyDictData> page = QueryUtils.buildPage(pageInfo, FlyDictData.class);
        QueryWrapper<FlyDictData> wp = QueryUtils.buildQueryWrapper(user, List.of("createTime"), FlyDictData.class);
        Page<FlyDictData> data = page(page, wp);
        wp.orderByDesc("update_time");
        return FlyPageInfo.of(data);
    }


    @Override
    public boolean switchStatus(String id, String status) {
        FlyDictData model = new FlyDictData();
        model
                //.setStatus(status)
                .setId(id);
        return this.updateById(model);
    }
}
