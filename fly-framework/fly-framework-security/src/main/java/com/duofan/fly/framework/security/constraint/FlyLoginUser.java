package com.duofan.fly.framework.security.constraint;

import cn.hutool.core.collection.CollUtil;
import com.duofan.fly.core.base.constant.security.SecurityConstant;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.entity.FlyUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * security 登录用户
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/20
 */
@Data
@AllArgsConstructor
public class FlyLoginUser implements UserDetails {

    private FlyUser user;
    private List<FlyResourceInfo> operations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return CollUtil.addAll(getRoleAuthority(), getOperationAuthority());
    }

    private Collection<? extends GrantedAuthority> getRoleAuthority() {
        // generate ROLE String ,role prefix
        return AuthorityUtils.createAuthorityList(operations.stream().map(info -> SecurityConstant.ROLE_PREFIX + info.getRoleNo()).distinct().toList());
    }

    private Collection<? extends GrantedAuthority> getOperationAuthority() {
        // generate op String ,operation prefix permission
        return AuthorityUtils.createAuthorityList(operations.stream()
                .map(flyResourceInfo -> String.
                        format("%s%s.%s", SecurityConstant.OPERATION_PREFIX,
                                flyResourceInfo.getModule(),
                                flyResourceInfo.getOp()))
                .filter(String::isBlank).distinct().toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
