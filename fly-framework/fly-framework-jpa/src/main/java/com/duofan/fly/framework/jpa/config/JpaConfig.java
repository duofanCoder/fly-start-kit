package com.duofan.fly.framework.jpa.config;

import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.framework.jpa.property.FlyJpaProperty;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Slf4j
@Configuration("jpaConfig")
@EnableConfigurationProperties(FlyJpaProperty.class)
public class JpaConfig {


    @Resource
    private FlyJpaProperty jpaProperty;

    @PostConstruct
    void cassandraManagedTypes(DefaultListableBeanFactory beanFactory) throws ClassNotFoundException {
        // 获取配置文件fly.jpa.scan-packages的值 增加com.duofan.fly.core.base.entity
        EntityScanPackages.register(beanFactory, "com.duofan.fly.core.base.entity");
        // 如果不为0注册
        if (jpaProperty.getScanPackages().length != 0) {
            EntityScanPackages.register(beanFactory, jpaProperty.getScanPackages());
        }
        log.info(LogConstant.COMPONENT_LOG, "JPA", EntityScanPackages.get(beanFactory).getPackageNames());

    }

}
