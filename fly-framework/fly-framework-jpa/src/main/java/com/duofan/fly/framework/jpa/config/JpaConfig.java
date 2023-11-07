package com.duofan.fly.framework.jpa.config;

import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.framework.jpa.property.FlyJpaProperty;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Slf4j
@Configuration("jpaConfig")
@EntityScan("com.duofan.fly.core.base.entity")
@EnableConfigurationProperties(FlyJpaProperty.class)
public class JpaConfig {


    @Resource
    private FlyJpaProperty jpaProperty;


    //    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("com.duofan.fly.core.base.entity"); // 默认的扫描包路径

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

        return entityManagerFactoryBean;
    }

    // 动态添加扫描包路径的方法
//    @Bean
//    @Primary
    public LocalContainerEntityManagerFactoryBean additionalEntityManagerFactory(
            DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan(jpaProperty.getScanPackages());  // 添加额外的扫描包路径

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);


        Map<String, Object> jpaProperties = entityManagerFactoryBean.getJpaPropertyMap();
        // 设置DDL操作，创建数据库表

        //JpaBaseConfiguration
        //JpaProperties
        // AbstractJpaVendorAdapter
//        HibernateJpaVendorAdapter
//        adapter.setShowSql(this.properties.isShowSql());
//        if (this.properties.getDatabase() != null) {
//
//            adapter.setDatabase(this.properties.getDatabase());
//        }
//        if (this.properties.getDatabasePlatform() != null) {
//            adapter.setDatabasePlatform(this.properties.getDatabasePlatform());
//        }
//        adapter.setGenerateDdl(this.properties.isGenerateDdl());
//        if (this.getDatabasePlatform() != null) {
//            jpaProperties.put("hibernate.dialect", this.getDatabasePlatform());
//        } else {
//            Class<?> databaseDialectClass = this.determineDatabaseDialectClass(this.getDatabase());
//            if (databaseDialectClass != null) {
//                jpaProperties.put("hibernate.dialect", databaseDialectClass.getName());
//            }
//        }

        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.connection.handling_mode", PhysicalConnectionHandlingMode.DELAYED_ACQUISITION_AND_HOLD);
        jpaProperties.put("hibernate.cdi.extensions", "true");

        log.info(LogConstant.COMPONENT_LOG, "JPA", "扫描路径【" + new ArrayList<>(Arrays.asList(jpaProperty.getScanPackages())) + "】");

        return entityManagerFactoryBean;
    }

}
