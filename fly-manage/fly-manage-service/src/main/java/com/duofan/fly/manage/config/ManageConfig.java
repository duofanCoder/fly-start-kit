package com.duofan.fly.manage.config;

import com.duofan.fly.core.storage.FlyFileMetaDataStorage;
import com.duofan.fly.manage.service.impl.FlyDefaultFileMetaDataStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManageConfig {
    
    @Bean
    @ConditionalOnMissingBean
    FlyFileMetaDataStorage flyDefaultFileMetaDataStorage(){
        return new FlyDefaultFileMetaDataStorage();
    }
}
