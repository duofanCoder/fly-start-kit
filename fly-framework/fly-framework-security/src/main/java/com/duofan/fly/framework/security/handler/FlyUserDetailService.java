package com.duofan.fly.framework.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDetails user = this.users.get(username.toLowerCase());
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
//                user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
        return null;

    }
}
