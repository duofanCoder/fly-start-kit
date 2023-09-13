package com.duofan.fly.core.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Configuration
@MapperScan(basePackages = "com.duofan.fly")
@EntityScan(basePackages = "com.duofan.fly")
public class BaseScanConfig {

}
