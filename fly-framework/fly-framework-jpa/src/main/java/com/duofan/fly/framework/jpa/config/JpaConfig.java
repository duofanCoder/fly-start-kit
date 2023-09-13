package com.duofan.fly.framework.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Configuration("jpaConfig")
@EntityScan(basePackages = "com.duofan.fly")
public class JpaConfig {

}
