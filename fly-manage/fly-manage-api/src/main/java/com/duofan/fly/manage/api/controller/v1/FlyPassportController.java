package com.duofan.fly.manage.api.controller.v1;

import com.alibaba.fastjson2.JSONObject;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.cache.constraint.CacheService;
import com.duofan.fly.framework.security.constraint.FlyLoginService;
import com.duofan.fly.framework.security.constraint.FlyRegisterService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
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
    @Resource
    private CacheService cacheService;
    @Resource
    private FlyLoginService loginService;
    @Resource
    private FlyRegisterService registerService;

    @PostMapping("/login")
    public FlyResult login(@RequestBody JSONObject loginRequest) {
        loginService.login(loginRequest);
        return FlyResult.SUCCESS;
    }

    @PostMapping("/register")
    public FlyResult register(@RequestBody @Validated RegisterRequest register) {
        FlyUser flyUser = new FlyUser();
        BeanUtils.copyProperties(register, flyUser);
        registerService.register(flyUser);
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

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RegisterRequest {
        @NotNull(message = "用户名不能为空")
        @NotBlank(message = "用户名不能为空")
        private String username;
        @NotNull(message = "密码不能为空")
        @NotBlank(message = "密码不能为空")
        private String password;
    }
}
