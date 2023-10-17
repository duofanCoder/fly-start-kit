package com.duofan.fly.manage.api.controller.response;

import com.duofan.fly.core.domain.FlyModule;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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

        public static List<MenuTree> of(@NotEmpty List<FlyModule> modules) {
            return modules.stream().map(
                    module -> {
                        MenuTree menu = new MenuTree();
                        menu.setLabel(module.getModuleName())
                                .setDescription(module.getDescription())
                                .setValue(module.getModule())
                                .setChildren(module.getApis().values().stream().map(
                                        api ->
                                                new MenuTree()
                                                        .setLabel(api.getOpName())
                                                        .setDescription(api.getDescription())
                                                        .setValue(menu.getValue() + "." + api.getOp())
                                                        .setActivated(api.isActivated())
                                                        .setDisabled(api.isGrantAll())
                                                        .setChildren(null)
                                ).toList());
                        return menu;
                    }
            ).toList();
        }

    }
}
