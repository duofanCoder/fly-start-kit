package com.duofan.fly.manage.api.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.common.FlyPageInfo;
import com.duofan.fly.core.base.domain.exception.FlyConstraintException;
import com.duofan.fly.core.base.domain.exception.FlyException;
import com.duofan.fly.core.base.domain.exception.FlySuspiciousSecurityException;
import com.duofan.fly.core.base.domain.permission.FlyRoleEnums;
import com.duofan.fly.core.base.entity.FlyRoleRel;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.dto.AdminLoginInfo;
import com.duofan.fly.core.dto.UserDto;
import com.duofan.fly.core.mapper.FlyUserMapper;
import com.duofan.fly.core.storage.FlyRoleStorage;
import com.duofan.fly.core.storage.FlyUserStorage;
import com.duofan.fly.core.utils.QueryUtils;
import com.duofan.fly.framework.security.constraint.FlyLoginUser;
import com.duofan.fly.framework.security.context.FlySessionHolder;
import com.duofan.fly.framework.security.property.SecurityProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Slf4j
@Component
public class FlyDefaultUserStorage extends ServiceImpl<FlyUserMapper, FlyUser> implements FlyUserStorage {
    @Resource
    private FlyRoleStorage roleStorage;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private FlyUserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public FlyUser getByUsername(String username) {
        LambdaQueryWrapper<FlyUser> wp = new LambdaQueryWrapper<FlyUser>();
        wp.eq(FlyUser::getUsername, username);
        return userMapper.selectOne(wp);
    }

    @Override
    public void passwdReset(UserDto userDto) {
        if (!FlySessionHolder.hasRole(FlyRoleEnums.ADMIN.getRoleNo()) && !FlySessionHolder.currentUsername().equals(userDto.getUsername())) {
            log.info(LogConstant.SUSPICIOUS_OPERATION_LOG + "，被修改用户名：{}", "重置密码", "越权修改其他用户密码", userDto.getUsername());
            throw new FlySuspiciousSecurityException("密码修改失败，请稍后再试");
        }

        FlyUser user = getByUsername(userDto.getUsername());
        String rawEncoderPasswd = passwordEncoder.encode(userDto.getRawPassword());
        String newEncoderPasswd = passwordEncoder.encode(userDto.getNewPassword());
        if (!user.getPassword().equals(rawEncoderPasswd)) {
            throw new FlyException("修改失败，原密码错误");
        }
        FlyUser flyUser = new FlyUser().setUsername(userDto.getUsername()).setPassword(newEncoderPasswd);
        flyUser.setId(user.getId());
        this.updateById(flyUser);
    }

    @Override
    public AdminLoginInfo getLoginUserInfo() {
        FlyLoginUser login = FlySessionHolder.currentUser();
        AdminLoginInfo info = new AdminLoginInfo();
        info.setRole(login.getRoleList().get(0)).setRoleList(login.getRoleList()).setUsername(login.getUsername()).setAuthorityList(login.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
        return info;
    }

    @Override
    public FlyPageInfo<FlyUser> page(FlyPageInfo<FlyUser> pageInfo, FlyUser user) {
        Page<FlyUser> page = QueryUtils.buildPage(pageInfo, FlyUser.class);
        QueryWrapper<FlyUser> wp = QueryUtils.buildQueryWrapper(user, List.of("username", "email", "phone", "isLocked", "isEnabled"), FlyUser.class);
        Page<FlyUser> data = page(page, wp);

        return FlyPageInfo.of(data);
    }

    @Override
    public FlyPageInfo<UserDto> page(FlyPageInfo<FlyUser> pageInfo, UserDto condition) {
        Page<FlyUser> page = QueryUtils.buildPage(pageInfo, FlyUser.class);
        IPage<UserDto> result = userMapper.page(page, condition);
        fillResult(result.getRecords());
        return FlyPageInfo.of(result);
    }

    @Override
    public void locked(FlyUser flyUser) {
        UpdateWrapper<FlyUser> wp = QueryUtils.buildUpdateWrapper(flyUser, List.of("username"), List.of("isLocked"), FlyUser.class);
        this.update(null, wp);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByUsername(FlyUser entity) {
        // 解析角色  并绑定角色关系
        updateRoleRelInfo(entity);
        // 更新除了用户名和密码的所有字段
        // , "email", "age", "birth", "phone", "isLocked", "isEnabled", "realName", "avatar", "idCardNo"
        UpdateWrapper<FlyUser> wp = QueryUtils.buildUpdateWrapper(entity, List.of("username"),
                List.of("remark", "email", "age", "birth", "phone", "isLocked", "isEnabled", "realName", "avatar", "idCardNo", "password")
                , FlyUser.class);
        super.update(null, wp);
    }

    @Override
    public boolean save(FlyUser entity) {
        existUser(entity);
        // 解析角色  并绑定角色关系
        updateRoleRelInfo(entity);
        entity.setPassword(passwordEncoder.encode(securityProperties.getDefaultPassword()));
        return super.save(entity);
    }

    private void existUser(FlyUser entity) {
        if (this.count(new LambdaQueryWrapper<FlyUser>().eq(FlyUser::getUsername, entity.getUsername())) > 0) {
            throw new FlyConstraintException("用户名已存在");
        }
    }

    protected void updateRoleRelInfo(FlyUser entity) {
        String roleNo = entity.getRoleNo();
        if (StrUtil.isNotBlank(roleNo)) {
            roleStorage.removeRel(new FlyRoleRel().setUsername(entity.getUsername()));
            Arrays.stream(roleNo.split(",")).forEach(item -> {
                // 保存角色关系
                roleStorage.addRoleRel(new FlyRoleRel(entity.getUsername(), item));
            });
        }
    }

    private void fillResult(List<UserDto> data) {
        data.forEach(item -> {
            List<String> list = roleStorage.getByUsername(item.getUsername());
            item.setRoleNo(CollUtil.isNotEmpty(list) ? CollUtil.join(list, ",") : "");
        });
    }


}
