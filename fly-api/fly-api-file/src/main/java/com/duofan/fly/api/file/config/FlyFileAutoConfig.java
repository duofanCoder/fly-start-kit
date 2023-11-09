package com.duofan.fly.api.file.config;

import com.duofan.fly.api.file.spi.FileStorageServiceFactory;
import com.duofan.fly.api.file.spi.FlyFileHandler;
import com.duofan.fly.api.file.spi.impl.FlyDefaultFileHandler;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.storage.FlyFileMetaDataStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/9
 */
@Slf4j
@Configuration
public class FlyFileAutoConfig {


    @Bean
    @ConditionalOnMissingBean
    FlyFileHandler flyFileHandler(FlyFileMetaDataStorage storage, FileStorageServiceFactory factory) {
        log.info(LogConstant.COMPONENT_LOG, "文件操作组件", "自动配置");
        return new FlyDefaultFileHandler(storage, factory);
    }
}