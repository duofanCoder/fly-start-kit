package com.duofan.fly.framework.security.context.dbAuth;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class FlyAuthorizationManager implements AuthorizationManager {


    @Override
    public AuthorizationDecision check(Supplier authentication, Object object) {
        return new AuthorizationDecision(true);
    }

    @Override
    public void verify(Supplier authentication, Object object) {
        AuthorizationManager.super.verify(authentication, object);
    }
}
