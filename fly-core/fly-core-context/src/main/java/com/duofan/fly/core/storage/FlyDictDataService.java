package com.duofan.fly.core.storage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.entity.FlyDictData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author duofan
 * @since 2023-12-03
 */
public interface FlyDictDataService extends IService<FlyDictData> {
    boolean save(FlyDictData entity);

    boolean edit(FlyDictData entity);

    FlyPageInfo<FlyDictData> page(FlyPageInfo<FlyDictData> pageInfo,FlyDictData entity);

    boolean switchStatus(String id, String status);
}
