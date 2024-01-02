package com.duofan.fly.manage.api.controller.v1.api;

import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.utils.WebUtils;
import com.duofan.fly.framework.security.constraint.FlyLoginService;
import com.duofan.fly.framework.security.constraint.FlyLogoutService;
import com.duofan.fly.framework.security.constraint.FlyRegisterService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/19
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/passport")
@FlyAccessInfo(moduleName = "登陆注册相关模块", system = "FLY BOOT")
public class FlyPassportController {
    @Resource
    private FlyLoginService loginService;
    // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        this.handler.logout(request, response, auth);
//        this.logoutSuccessHandler.onLogoutSuccess(request, response, auth);
//        return;
    @Resource
    private FlyLogoutService logoutService;
    @Resource
    private FlyRegisterService registerService;

    @Resource
    private HttpServletRequest request;

    @PostMapping("/login")
    @FlyAccessInfo(opName = "登陆", needAuthenticated = false)
    public FlyResult login(@RequestBody @Valid Map<String, Object> loginRequest) {
        loginRequest.put("ip", WebUtils.getIp(request));
        return FlyResult.success(loginService.login(loginRequest));
    }

    @PostMapping("/register")
    @FlyAccessInfo(opName = "注册", needAuthenticated = false)
    public FlyResult register(@RequestBody @Valid RegisterRequest register) {
        FlyUser flyUser = new FlyUser();
        BeanUtils.copyProperties(register, flyUser);
        registerService.register(flyUser);
        return FlyResult.SUCCESS;
    }

    @PostMapping("/logout")
    @FlyAccessInfo(opName = "注销", isGrantToAll = true)
    public FlyResult logout() {
        logoutService.logout();
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
