package com.duofan.fly.manage.api.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.dto.UserDto;
import com.duofan.fly.core.storage.FlyRoleStorage;
import com.duofan.fly.core.storage.FlyUserStorage;
import com.duofan.fly.manage.api.controller.request.UserRequest;
import jakarta.annotation.Resource;
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
@RequestMapping("/api/v1/user")
@FlyAccessInfo(system = "FLY BOOT", moduleName = "用户操作")
public class FlyUserController {

    @Resource
    private FlyUserStorage userStorage;

    @Resource
    private FlyRoleStorage roleStorage;

    @PostMapping("/password/reset")
    @FlyAccessInfo(opName = "重置密码")
    public FlyResult passwdReset(UserRequest.PasswdReset request) {
        userStorage.passwdReset(BeanUtil.copyProperties(request, UserDto.class));
        return FlyResult.SUCCESS;
    }


    @PostMapping("/getUserInfo")
    @FlyAccessInfo(opName = "获取用户信息")
    public FlyResult getUserInfo() {
        return FlyResult.success(userStorage.getLoginUserInfo());
    }


    @PostMapping("/page")
    @FlyAccessInfo(opName = "分页用户信息")
    public FlyResult page(FlyPageInfo<FlyUser> pageInfo, UserRequest.UserPage page) {
        return FlyResult.success(userStorage.page(pageInfo, BeanUtil.copyProperties(page, UserDto.class)));
    }

    @PostMapping("/detail")
    @FlyAccessInfo(opName = "详细用户信息")
    public FlyResult detail(String id) {
        return FlyResult.success(userStorage.getById(id));
    }

    @PostMapping("/delete")
    @FlyAccessInfo(opName = "删除用户信息")
    public FlyResult delete(String id) {
        return FlyResult.success(userStorage.removeById(id));
    }

    @PostMapping("/update")
    @FlyAccessInfo(opName = "修改用户信息")
    public FlyResult updateById(UserRequest.UserUpdate request) {
        return FlyResult.success(userStorage.updateById(BeanUtil.copyProperties(request, FlyUser.class)));
    }


}
