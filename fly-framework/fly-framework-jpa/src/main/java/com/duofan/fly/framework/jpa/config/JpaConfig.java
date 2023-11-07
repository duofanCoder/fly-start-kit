package com.duofan.fly.framework.jpa.config;

import cn.hutool.core.collection.CollUtil;
import com.duofan.fly.framework.jpa.property.FlyJpaProperty;
import jakarta.annotation.Resource;
import lombok.val;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Configuration("jpaConfig")
@EnableConfigurationProperties(FlyJpaProperty.class)
public class JpaConfig {


    @Resource
    private FlyJpaProperty jpaProperty;

    @Bean
    @ConditionalOnProperty(name = "fly.jpa.ddl-auto.enabled", havingValue = "true")
    public EntityScanPackages cassandraManagedTypes(DefaultListableBeanFactory beanFactory) throws ClassNotFoundException {
        val entityScanPackages = EntityScanPackages.get(beanFactory);
        // 获取配置文件fly.jpa.scan-packages的值
        Collection<String> scanPackages = CollUtil.addAll(Arrays.stream(jpaProperty.getScanPackages()).toList(), "com.duofan.fly.core.base.entity");
        EntityScanPackages.register(beanFactory, scanPackages);
        return entityScanPackages;
    }

}
