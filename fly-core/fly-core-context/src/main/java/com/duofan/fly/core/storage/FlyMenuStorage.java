package com.duofan.fly.core.storage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.duofan.fly.core.base.entity.FlyMenu;

import java.util.List;

/**
 * 菜单操作抽象
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/10
 */
public interface FlyMenuStorage extends IService<FlyMenu> {

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu   菜单信息
     * @param roleNo roleNo
     * @return 菜单列表
     */
    public List<FlyMenu> selectMenuList(FlyMenu menu, String roleNo);
}
