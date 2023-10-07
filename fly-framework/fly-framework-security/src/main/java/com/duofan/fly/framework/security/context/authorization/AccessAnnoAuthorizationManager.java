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
        if (attribute.isGrantToAll()) {
            return new AuthorizationDecision(true);
        }
        Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();

        if (authorities
                .contains(new SimpleGrantedAuthority(SecurityConstant.ROLE_PREFIX + FlyRoleEnums.ADMIN))) {
            return new AuthorizationDecision(true);
        }

        if (authorities.contains(
                new SimpleGrantedAuthority(attribute.getFullOp())
        )) {
            return new AuthorizationDecision(true);
        }

        return new AuthorizationDecision(false);
    }
}
