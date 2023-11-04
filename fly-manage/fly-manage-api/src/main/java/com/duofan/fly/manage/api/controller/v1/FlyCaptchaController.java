package com.duofan.fly.manage.api.controller.v1;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.StrUtil;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/api/v1/captcha")
@FlyAccessInfo(moduleName = "校验管理", system = "FLY BOOT")
public class FlyCaptchaController {

    @Resource
    private FlyCacheService cacheService;
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 4;

    private final String cacheKey = "captchaCode:{}";

    @GetMapping("/captcha")
    @FlyAccessInfo(opName = "生成验证码", description = "生成验证码", needAuthenticated = false)
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(WIDTH, HEIGHT, CODE_LENGTH, 4);
        String code = circleCaptcha.getCode();
        cacheService.set(StrUtil.format(cacheKey, request.getSession().getId()), code, Duration.ofMinutes(3));
        circleCaptcha
                .write(response.getOutputStream());
    }

    @PostMapping("/validateCaptcha")
    @FlyAccessInfo(opName = "校验验证码", description = "校验验证码", needAuthenticated = false)
    public FlyResult validateCaptcha(@RequestParam String inputCaptcha, HttpServletRequest request) {
        // 从Session中获取之前生成的验证码
        String key = StrUtil.format(cacheKey, request.getSession().getId());
        // 检查用户输入的验证码是否与Session中的验证码匹配
        if (cacheService.hasKey(key) && inputCaptcha.equalsIgnoreCase(String.valueOf(cacheService.get(key)))) {
            cacheService.delete(key);
            // 验证码匹配，返回验证成功信息
            return new FlyResult("200", "验证成功", null);
        } else {
            // 验证码不匹配，返回验证失败信息
            return new FlyResult("500", "验证失败", null);
        }
    }
}
