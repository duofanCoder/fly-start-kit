package com.duofan.fly.api.file.config;

import com.duofan.fly.api.file.propterty.FileStorageProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/9
 */
@Configuration
@EnableConfigurationProperties(FileStorageProperty.class)
public class FileStorageConfig {
}
