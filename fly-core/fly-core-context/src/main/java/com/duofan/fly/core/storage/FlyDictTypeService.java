package com.duofan.fly.core.storage;


import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.entity.FlyDictType;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author duofan
 * @since 2023-12-03
 */
public interface FlyDictTypeService extends IService<FlyDictType> {
    boolean save(FlyDictType entity);

    boolean edit(FlyDictType entity);

    FlyPageInfo<FlyDictType> page(FlyPageInfo<FlyDictType> pageInfo,FlyDictType entity);

    boolean switchStatus(String id, String status);
}
