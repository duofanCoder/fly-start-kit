package com.duofan.fly.framework.mvc.controller;

import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/error")
@FlyAccessInfo(moduleName = "内部错误回调模块", system = "FLY BOOT")
public class ErrorController {
    
    @GetMapping("/jwtFilter")
    @FlyAccessInfo(opName = "jwt错误回调", needAuthenticated = false)
    public void jwtFilterException(HttpServletRequest request) throws Exception {
        throw (Exception) request.getAttribute("jwtFilter.error");
    }
}
