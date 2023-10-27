package com.duofan.fly.core.spi;


import com.duofan.fly.core.base.entity.FlyDict;

import java.util.List;

public interface DictExtension {
    String getType();

    List<FlyDict> list();

    boolean isCacheable();
}