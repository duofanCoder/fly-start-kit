package com.duofan.fly.framework.security.config;

import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.exception.FlyConstraintException;
import com.duofan.fly.core.mapper.FlyRoleMapper;
import com.duofan.fly.core.mapper.FlyUserMapper;
import com.duofan.fly.framework.security.constraint.impl.FlyUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collection;

@Slf4j
@Configuration
public class ServerModeConfig {

    @Bean
    @ConditionalOnProperty(prefix = "fly.security", name = "admin-mode", matchIfMissing = true)
    public FlyUserDetailService flyUserDetailService(FlyUserMapper userMapper, FlyRoleMapper roleMapper) {
        return new FlyUserDetailService(userMapper, roleMapper);
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        log.error(LogConstant.COMPONENT_LOG,"Security组件","用户端使用security请配置UserDetailsService的实现类");
        return username -> User.builder().username(username).password("123456").authorities("ROLE_USER").build();
    }
}
