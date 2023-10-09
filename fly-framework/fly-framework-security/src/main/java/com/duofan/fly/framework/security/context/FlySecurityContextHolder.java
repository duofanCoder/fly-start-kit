package com.duofan.fly.framework.security.context;

import com.duofan.fly.core.base.constant.security.SecurityConstant;
import com.duofan.fly.framework.security.constraint.FlyLoginUser;
import com.duofan.fly.framework.security.exception.FlySecurityException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * 用户安全上下文
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/24
 */
public class FlySecurityContextHolder {

    public static FlyLoginUser currentUser() {
        return (FlyLoginUser) Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).orElseThrow(
                () -> new FlySecurityException("当前用户信息不存在")
        );
    }


    public static boolean hasRole(String roleNo) {
        return currentUser().getAuthorities().contains(new SimpleGrantedAuthority(SecurityConstant.ROLE_PREFIX + roleNo));
    }

    public static String currentUsername() {
        return currentUser().getUsername();
    }

}
