package com.duofan.fly.manage.api.controller.v1;

import com.alibaba.fastjson2.JSONObject;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.framework.security.constraint.FlyLoginService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/19
 */

@Slf4j
@RestController
@RequestMapping("passport")
public class FlyPassportController {

    @Resource(type = FlyLoginService.class)
    private FlyLoginService loginService;

    @PostMapping("/login")
    public FlyResult login(@RequestBody JSONObject loginRequest) {
        loginService.login(loginRequest);
        return FlyResult.SUCCESS;
    }

    @PostMapping("/logout")
    public FlyResult logout() {
        return FlyResult.SUCCESS;
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
