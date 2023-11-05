package com.duofan.fly.api.verification.controller.v1;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.spi.cahce.FlyCacheService;
import com.duofan.fly.core.utils.CacheKeyUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/api/v1/captcha")
@FlyAccessInfo(moduleName = "验证码校验管理", system = "FLY BOOT")
public class FlyCaptchaController {

    @Resource
    private FlyCacheService cacheService;

    @Value("${fly.verification.captcha.width:120}")
    private final int WIDTH = 120;
    @Value("${fly.verification.captcha.height:40}")
    private final int HEIGHT = 40;
    @Value("${fly.verification.captcha.code-length:4}")
    private final int CODE_LENGTH = 4;
    @Value("${fly.verification.captcha.effective-time:3}")
    private final Integer effectiveTime = 3;


    @GetMapping("/generateCaptcha")
    @FlyAccessInfo(opName = "生成验证码", description = "生成验证码", needAuthenticated = false)
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(WIDTH, HEIGHT, CODE_LENGTH, 4);
        String code = circleCaptcha.getCode();
        cacheService.set(CacheKeyUtils.getCaptchaCacheKey(request), code, Duration.ofMinutes(effectiveTime));
        circleCaptcha
                .write(response.getOutputStream());
    }

    @PostMapping("/validateCaptcha")
    @FlyAccessInfo(opName = "校验验证码", description = "校验验证码", needAuthenticated = false)
    public FlyResult validateCaptcha(@RequestParam String inputCaptcha, @RequestParam String nextId, HttpServletRequest request) {
        String key = CacheKeyUtils.getCaptchaCacheKey(request);
        // 检查用户输入的验证码是否与Session中的验证码匹配
        if (cacheService.hasKey(key) && inputCaptcha.equalsIgnoreCase(String.valueOf(cacheService.get(key)))) {
            cacheService.delete(key);
            cacheService.set(key, "true", Duration.ofMinutes(30));
            // 验证码匹配，返回验证成功信息
            return new FlyResult("200", "验证成功", null);
        } else {
            // 验证码不匹配，返回验证失败信息
            return new FlyResult("500", "验证失败", null);
        }
    }


}
