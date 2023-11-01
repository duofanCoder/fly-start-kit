package com.duofan.fly.api.pay.controller;

import com.duofan.fly.api.pay.service.PayService;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/1
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/pay")
@FlyAccessInfo(moduleName = "支付管理", system = "FLY BOOT - PAY")
public class FlyPayController {

    @Resource
    private PayService payService;


    @PostMapping("/create")
    @FlyAccessInfo(opName = "创建支付", needAuthenticated = false)
    FlyResult create(@RequestBody @Valid Double price) {
        return FlyResult.success(payService.preCreate(price));
    }


}
