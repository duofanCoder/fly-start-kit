package com.duofan.fly.core.storage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.entity.FlyDict;

import java.util.List;

/**
 * 字典管理接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/16
 */
public interface FlyDictStorage extends IService<FlyDict> {
    List<FlyDict> list(List<String> typeList);

    boolean save(FlyDict dict);

    void update(FlyDict dict);

    FlyPageInfo<FlyDict> page(FlyPageInfo<FlyDict> pageInfo, FlyDict condition);

}
