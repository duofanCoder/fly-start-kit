package com.duofan.fly.framework.security.constraint.impl;

import com.duofan.fly.core.base.entity.FlyUser;
import com.duofan.fly.core.storage.FlyUserStorage;
import com.duofan.fly.core.storage.impl.FlyDefaultUserStorage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 用户查询
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/15
 */
@Slf4j
@Component
public class FlyUserDetailService implements UserDetailsService {
    @Resource(type = FlyDefaultUserStorage.class)
    private FlyUserStorage userStorage;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FlyUser user = Optional.of(this.userStorage.selectByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误"));

        return null;
    }
}
