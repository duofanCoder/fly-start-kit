package com.duofan.fly.manage.generator.web;

import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.manage.generator.AutoGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gen")
@FlyAccessInfo(system = "FLY BOOT")
public class TemplateConfigController {

    @GetMapping("/")
    @FlyAccessInfo(module = "模板配置", op = "查询", description = "查询模板配置列表", needAuthenticated = false)
    String index() {
        return "index";
    }

    @GetMapping("list")
    @FlyAccessInfo(module = "模板1配置", op = "查询", description = "查询模板配置列表", needAuthenticated = false)
    String list() {
        return "list";
    }

}
