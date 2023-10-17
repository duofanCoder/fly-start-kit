package com.duofan.fly.core.spi;


import com.duofan.fly.core.base.entity.FlyDict;

import java.util.List;

public interface DicExtension {
    String getType();

    List<FlyDict> list();

    boolean isCacheable();
}
