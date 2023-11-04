package com.duofan.fly.manage.api.controller.v1;

import cn.hutool.core.util.RandomUtil;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.spi.message.VerificationCodeSender;
import com.duofan.fly.manage.api.controller.request.MessageRequest;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * 消息接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/4
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/message")
@FlyAccessInfo(moduleName = "消息管理", system = "FLY BOOT")
public class FlyMessageController {

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Resource
    private VerificationCodeSender sender;


    @PostMapping("/code/email")
    @FlyAccessInfo(opName = "发邮件验证码", description = "发个邮件", needAuthenticated = false)
    public FlyResult codeEmail(@RequestBody @Valid MessageRequest.SendTo info) {
        taskExecutor.execute(
                () -> {
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("verificationCode", RandomUtil.randomNumbers(6));
                    try {
                        sender.sendVerificationCode(Set.of(info.getTo()), null, params);
                        log.info(LogConstant.RESOURCE_USE_LOG, "邮件资源", "发送邮件验证码到【" + info.getTo() + "】");
                    } catch (IOException e) {
                        log.error("邮箱发送用户【{}】失败,{}", info.getTo(), e.getMessage());
                    }
                }
        );
        return FlyResult.SUCCESS;
    }

    @PostMapping("/code/phone")
    @FlyAccessInfo(opName = "发短信验证码", description = "发个短信", needAuthenticated = false)
    public FlyResult codePhone(@Valid MessageRequest.SendTo info) throws IOException {
        return FlyResult.SUCCESS;
    }
}
