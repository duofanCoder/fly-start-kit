package com.duofan.fly.api.file.config;

import com.duofan.fly.api.file.spi.FlyFileHandler;
import com.duofan.fly.api.file.spi.impl.FlyDefaultFileHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 自动配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/9
 */
@AutoConfiguration
public class FlyFileAutoConfig {

    @Bean
    FlyFileHandler flyFileHandler() {
        return new FlyDefaultFileHandler();
    }
}