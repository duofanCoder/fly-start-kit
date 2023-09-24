package com.duofan.fly.manage.api.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.domain.FlyUserDto;
import com.duofan.fly.core.storage.FlyUserStorage;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/24
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class FlyUserController {

    @Resource
    private FlyUserStorage userStorage;

    
    @PostMapping("/password/reset")
    public FlyResult passwdReset(PasswdResetRequest request) {
        userStorage.passwdReset(BeanUtil.copyProperties(request, FlyUserDto.class));
        return FlyResult.SUCCESS;
    }

    @Data
    private static class PasswdResetRequest {
        private String username;
        private String rawPassword;
        private String newPassword;
    }
}
