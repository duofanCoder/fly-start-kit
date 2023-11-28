package com.duofan.fly.manage.generator.web;

import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/datasource")
@FlyAccessInfo(system = "FLY BOOT")
public class DataSourceController {
    @GetMapping("/")
    @FlyAccessInfo(module = "模板配置", op = "查询", description = "查询模板配置列表", needAuthenticated = false)
    public String index() {
        return "index";
    }


}
