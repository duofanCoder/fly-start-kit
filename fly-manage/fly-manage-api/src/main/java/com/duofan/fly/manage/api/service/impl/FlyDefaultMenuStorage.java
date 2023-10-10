package com.duofan.fly.manage.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.entity.FlyMenu;
import com.duofan.fly.core.mapper.FlyMenuMapper;
import com.duofan.fly.core.storage.FlyMenuStorage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单操作
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/10
 */
@Slf4j
@Service
public class FlyDefaultMenuStorage extends ServiceImpl<FlyMenuMapper, FlyMenu> implements FlyMenuStorage {

    @Resource
    private FlyMenuMapper mapper;


    @Override
    public List<FlyMenu> selectMenuList(FlyMenu menu, String roleNo) {
        // FlySessionHolder.currentUser()
        return null;
    }
}
