package com.duofan.fly.manage.api.dict;

import com.duofan.fly.core.base.domain.common.FlyDictionary;
import com.duofan.fly.core.base.entity.FlyDictData;
import com.duofan.fly.core.spi.DictExtension;
import com.duofan.fly.core.storage.FlyRoleStorage;
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
public class RoleDict implements DictExtension {
    @Resource
    private FlyRoleStorage service;

    @Override
    public String getType() {
        return "roleDict";
    }

    @Override
    public List<FlyDictionary> list() {
        return service.list().stream()
                .map(i -> new FlyDictionary(getType(), i.getRoleName(), i.getRoleNo()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

}
