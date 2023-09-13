package com.duofan.fly.framework.mybatis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/11
 */
@Configuration("mybatisConfig")
@MapperScan(basePackages = "com.duofan.fly")
public class MybatisConfig {


}
