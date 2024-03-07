package com.duofan.fly.framework.security.context;

import cn.hutool.jwt.JWT;

public enum AuthenticationMethod {
    NONE ,
    JWT,
    REDIS,
    SSO
}
