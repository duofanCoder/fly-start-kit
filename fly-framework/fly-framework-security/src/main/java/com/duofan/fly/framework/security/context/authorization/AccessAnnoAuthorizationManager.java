package com.duofan.fly.framework.security.context.authorization;

import com.duofan.fly.core.base.domain.permission.FlyResourceInfo;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

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

        // TODO 角色对应到用户操作关系 - 如何保存上下文
//        authentication.get().getPrincipal()
        return new AuthorizationDecision(false);
    }
}
