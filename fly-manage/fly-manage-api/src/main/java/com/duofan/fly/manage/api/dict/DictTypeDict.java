package com.duofan.fly.manage.api.dict;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.duofan.fly.core.base.entity.FlyDict;
import com.duofan.fly.core.mapper.FlyDictMapper;
import com.duofan.fly.core.spi.DictExtension;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/7/14
 */
@Component
public class DictTypeDict implements DictExtension {
    @Resource
    private FlyDictMapper mapper;

    @Override
    public String getType() {
        return "dictTypeDict";
    }

    @Override
    public List<FlyDict> list() {
        return mapper.selectList(
                        new LambdaQueryWrapper<FlyDict>()
                                .groupBy(FlyDict::getType)
                ).stream()
                .map(i -> new FlyDict(getType(), i.getName(), i.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

}
