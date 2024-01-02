package com.duofan.fly.api.verification.controller.v1.api;

import cn.hutool.captcha.ICaptcha;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.spi.FlyCaptchaService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/captcha")
@FlyAccessInfo(moduleName = "验证码校验管理", system = "FLY BOOT")
public class FlyCaptchaController {

    @Resource
    private FlyCaptchaService captchaService;


    /**
     * 验证逻辑-生成验证码，验证的时候传入验证成功后访问的接口
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/generateCaptcha")
    @FlyAccessInfo(opName = "生成验证码", description = "生成验证码", needAuthenticated = false)
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        ICaptcha captcha = captchaService.createCaptcha(request);
        captcha
                .write(response.getOutputStream());
    }

    /**
     * 验证逻辑-校验验证码
     *
     * @param inputCaptcha 输入的验证码
     * @param nextId       验证成功后访问的接口 没有使用，但是必须传入，会通过request取
     * @param request
     * @return
     */
    @PostMapping("/validateCaptcha")
    @FlyAccessInfo(opName = "校验验证码", description = "校验验证码", needAuthenticated = false)
    public FlyResult validateCaptcha(@RequestParam String inputCaptcha, @RequestParam String nextId, HttpServletRequest request) {

        boolean isSuccess = captchaService.checkCaptcha(inputCaptcha, request);
        if (isSuccess) {
            return new FlyResult("200", "验证成功", null);
        } else {
            // 验证码不匹配，返回验证失败信息
            return new FlyResult("500", "验证失败", null);
        }
    }


}
