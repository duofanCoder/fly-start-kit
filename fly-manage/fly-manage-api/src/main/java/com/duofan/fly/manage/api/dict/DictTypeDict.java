package com.duofan.fly.manage.api.dict;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.duofan.fly.core.base.entity.FlyDictData;
import com.duofan.fly.core.mapper.FlyDictDataMapper;
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
    private FlyDictDataMapper mapper;

    @Override
    public String getType() {
        return "dictTypeDict";
    }

    @Override
    public List<FlyDictData> list() {
        return mapper.selectList(
                        new LambdaQueryWrapper<FlyDictData>()
                                .groupBy(FlyDictData::getType)
                ).stream()
                .map(i -> new FlyDictData(getType(), i.getLabel(), i.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

}
