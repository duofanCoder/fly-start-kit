package com.duofan.fly.manage.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.entity.FlyDictType;
import com.duofan.fly.core.mapper.FlyDictTypeMapper;
import com.duofan.fly.core.storage.FlyDictTypeService;
import com.duofan.fly.core.utils.QueryUtils;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author duofan
 * @since 2023-12-03
 */
@Service
public class FlyDictTypeServiceImpl extends ServiceImpl<FlyDictTypeMapper, FlyDictType> implements FlyDictTypeService {
    @Resource
    private FlyDictTypeMapper mapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean save(FlyDictType entity){
        entity.setId(null);
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean edit(FlyDictType entity){
        return updateById(entity);
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
}
