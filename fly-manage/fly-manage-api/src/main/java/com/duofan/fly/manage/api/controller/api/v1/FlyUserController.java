package com.duofan.fly.manage.api.controller.api.v1;

import cn.hutool.core.bean.BeanUtil;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.dto.UserDto;
import com.duofan.fly.core.storage.FlyUserStorage;
import com.duofan.fly.manage.api.controller.request.UserRequest;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/password/reset")
    @FlyAccessInfo(opName = "重置密码")
    public FlyResult passwdReset(UserRequest.PasswdReset request) {
        userStorage.passwdReset(BeanUtil.copyProperties(request, UserDto.class));
        return FlyResult.SUCCESS;
    }


    @PostMapping("/getUserInfo")
    @FlyAccessInfo(opName = "获取用户信息", isGrantToAll = true)
    public FlyResult getUserInfo() {
        return FlyResult.success(userStorage.getLoginUserInfo());
    }


    @GetMapping("/page")
    @FlyAccessInfo(opName = "分页用户信息")
    public FlyResult page(FlyPageInfo<FlyUser> pageInfo, UserRequest.UserPage page) {
        return FlyResult.success(userStorage.page(pageInfo, BeanUtil.copyProperties(page, UserDto.class)));
    }

    @GetMapping("/detail")
    @FlyAccessInfo(opName = "详细用户信息")
    public FlyResult detail(String id) {
        return FlyResult.success(userStorage.getById(id));
    }

    @DeleteMapping("/delete")
    @FlyAccessInfo(opName = "删除用户信息")
    public FlyResult delete(String id) {
        return FlyResult.success(userStorage.removeById(id));
    }

    @PostMapping("/update")
    @FlyAccessInfo(opName = "修改用户信息")
    public FlyResult updateByUsername(@RequestBody @Valid UserRequest.UserUpdate request) {
        userStorage.updateByUsername(BeanUtil.copyProperties(request, FlyUser.class));
        return FlyResult.SUCCESS;
    }

    @PostMapping("/save")
    @FlyAccessInfo(opName = "添加用户信息")
    public FlyResult save(@RequestBody @Valid UserRequest.Save request) {
        return FlyResult.success(userStorage.save(BeanUtil.copyProperties(request, FlyUser.class)));
    }

    @PostMapping("/status/locked")
    @FlyAccessInfo(opName = "启用用户")
    public FlyResult enabled(@RequestBody @Valid UserRequest.Locked request) {
        userStorage.locked(BeanUtil.copyProperties(request, FlyUser.class));
        return FlyResult.SUCCESS;
    }
}
