package com.duofan.fly.api.verification.config;

import com.duofan.fly.api.verification.proptery.VerificationProperty;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 当面副
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/27
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({VerificationProperty.class})
public class FlyVerificationConfig {

    @Resource
    private VerificationProperty property;


}
