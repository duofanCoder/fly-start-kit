package com.duofan.fly.framework.security.context.authorization;

import com.duofan.fly.core.base.constant.security.SecurityConstant;
import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import com.duofan.fly.core.base.domain.permission.FlyRoleEnums;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Supplier;

@Component
public class AccessAnnoAuthorizationManager implements AuthorizationManager<MethodInvocation> {
    private final AccessAnnoAttributeRegistry registry = new AccessAnnoAttributeRegistry();

    @Override
    public void verify(Supplier<Authentication> authentication, MethodInvocation object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation object) {
        FlyResourceInfo attribute = registry.getAttribute(object);
        Authentication authInfo = authentication.get();
        Collection<? extends GrantedAuthority> authorities = authInfo.getAuthorities();

        // ADMIN 角色直接通过
        if (authorities
                .contains(new SimpleGrantedAuthority(SecurityConstant.ROLE_PREFIX + FlyRoleEnums.ADMIN))) {
            return new AuthorizationDecision(true);
        }

        // TODO 是否有必要做这个拦截应当不会走到这里，需要认证的授权处理执行check
        if (!attribute.isNeedAuthenticated()) {
            return new AuthorizationDecision(true);
        }
        
        if (attribute.isGrantToAll() && !authInfo.getAuthorities().contains(
                new SimpleGrantedAuthority(SecurityConstant.AUTHORITY_ROLE_ANONYMOUS)
        )) {
            return new AuthorizationDecision(true);
        }

        // 访问权限控制是通过模块和操作来控制的
        // 不是通过url来控制的，因此请求method是不影响判断的。只要是op值不同即可
        if (authorities.contains(
                new SimpleGrantedAuthority(attribute.getFullOp())
        )) {
            return new AuthorizationDecision(true);
        }

        return new AuthorizationDecision(false);
    }
}
