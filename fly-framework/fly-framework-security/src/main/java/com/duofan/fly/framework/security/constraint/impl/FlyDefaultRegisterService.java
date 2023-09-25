package com.duofan.fly.framework.security.constraint.impl;

import com.duofan.fly.core.base.domain.permission.FlyRoleEnums;
import com.duofan.fly.core.base.entity.FlyRoleRel;
import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.storage.FlyRoleStorage;
import com.duofan.fly.core.storage.FlyUserStorage;
import com.duofan.fly.framework.security.constraint.FlyRegisterService;
import com.duofan.fly.framework.security.exception.RegisterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/23
 */
@Slf4j
public class FlyDefaultRegisterService implements FlyRegisterService {

    private final FlyUserStorage userStorage;

    private final FlyRoleStorage roleStorage;

    private final PasswordEncoder passwordEncoder;

    public FlyDefaultRegisterService(FlyUserStorage userStorage, FlyRoleStorage roleStorage, PasswordEncoder passwordEncoder) {
        this.userStorage = userStorage;
        this.roleStorage = roleStorage;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(FlyUser data) {
        // 密码加密保存
        data.setPassword(passwordEncoder.encode(data.getPassword()));
        Optional.ofNullable(userStorage.getByUsername(data.getUsername())).ifPresent((i) -> {
            throw new RegisterException("用户名已存在");
        });
        userStorage.save(data);
        roleStorage.addRoleRel(new FlyRoleRel(data.getUsername(), FlyRoleEnums.DEFAULT.getRoleNo()));
    }

}
