package com.duofan.fly.framework.security.config;

import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.mapper.FlyRoleMapper;
import com.duofan.fly.core.mapper.FlyUserMapper;
import com.duofan.fly.framework.security.constraint.impl.FlyUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

@Slf4j
@Configuration
public class ServerModeConfig {

    @Bean
    @ConditionalOnProperty(prefix = "fly.security", name = "admin-mode", matchIfMissing = true)
    @ConditionalOnMissingBean(UserDetailsService.class)
    public FlyUserDetailService flyUserDetailService(FlyUserMapper userMapper, FlyRoleMapper roleMapper) {
        return new FlyUserDetailService(userMapper, roleMapper);
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        log.error(LogConstant.COMPONENT_LOG, "Security组件", "用户端使用security请配置UserDetailsService的实现类");
        return username -> User.builder().username(username).password("123456").authorities("ROLE_USER").build();
    }
}
