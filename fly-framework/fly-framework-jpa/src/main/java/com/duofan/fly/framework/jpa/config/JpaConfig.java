package com.duofan.fly.framework.jpa.config;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Configuration("jpaConfig")
public class JpaConfig {

    @Resource
    DefaultListableBeanFactory factory;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.duofan.fly.cooo");
        factory.setDataSource(dataSource);
        return factory;
    }

//    @Bean
//    @ConditionalOnProperty(prefix = "fly.jpa.ddl-auto", name = "enabled")
//    public static EntityScanPackages cassandraManagedTypes(DefaultListableBeanFactory beanFactory) throws ClassNotFoundException {
//        val entityScanPackages = EntityScanPackages.get(beanFactory);
//        EntityScanPackages.register(beanFactory, "com.duofan.fly");
//        return entityScanPackages;
//    }

}
