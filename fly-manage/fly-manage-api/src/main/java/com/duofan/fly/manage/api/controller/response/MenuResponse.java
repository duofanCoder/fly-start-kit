package com.duofan.fly.manage.api.controller.response;

import com.duofan.fly.core.domain.FlyModule;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单返回构造
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/16
 */
public class MenuResponse {

    @Data
    @Accessors(chain = true)
    public static class MenuTree {
        private String label;
        private String value;
        private String description;
        private boolean isActivated;
        // 无法设置，针对权限是授予所有人的
        private boolean disabled;
        private List<MenuTree> children;

        public static Map<String, Object> of(@NotEmpty List<FlyModule> modules) {
            ArrayList<String> checkMenus = new ArrayList<>();
            List<MenuTree> tree = modules.stream().map(
                    module -> {
                        MenuTree menu = new MenuTree();
                        menu.setLabel(module.getModuleName())
                                .setDescription(module.getDescription())
                                .setValue(module.getModule())
                                .setChildren(module.getApis().values().stream().map(
                                        api ->
                                        {
                                            val tmp = new MenuTree()
                                                    .setLabel(api.getOpName())
                                                    .setDescription(api.getDescription())
                                                    .setValue(menu.getValue() + "." + api.getOp())
                                                    .setActivated(api.isActivated() || api.isGrantAll() || !api.isNeedAuthenticated())
                                                    .setDisabled(api.isGrantAll() || !api.isNeedAuthenticated())
                                                    .setChildren(null);
                                            if (tmp.isActivated() || tmp.isDisabled()) {
                                                checkMenus.add(tmp.getValue());
                                            }
                                            return tmp;
                                        }
                                ).toList());
                        return menu;
                    }
            ).toList();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("checkMenus", checkMenus);
            map.put("tree", tree);
            return map;
        }

    }
}
