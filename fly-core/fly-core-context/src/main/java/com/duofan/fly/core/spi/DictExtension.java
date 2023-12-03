package com.duofan.fly.core.spi;


import com.duofan.fly.core.base.domain.common.FlyDictionary;
import com.duofan.fly.core.base.entity.FlyDictData;

import java.util.List;

public interface DictExtension {
    String getType();

    List<FlyDictionary> list();

    boolean isCacheable();
}
