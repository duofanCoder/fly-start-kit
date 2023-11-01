package com.duofan.fly.api.pay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.duofan.fly.api.pay.utils.AlipayTradeProperty;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

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
@EnableConfigurationProperties(AlipayTradeProperty.class)
public class AlipayFaceTradeConfig {

    @Resource
    private AlipayTradeProperty.FaceTradeProperties faceTradeProperty;

    private AlipayConfig getDefaultConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId(faceTradeProperty.getAppId());
        alipayConfig.setPrivateKey(faceTradeProperty.getPrivateKey());
        alipayConfig.setAlipayPublicKey(faceTradeProperty.getPublicKey());
        alipayConfig.setEncryptKey(faceTradeProperty.getEncryptKey());
        alipayConfig.setServerUrl(faceTradeProperty.getServerUrl());
        alipayConfig.setFormat(faceTradeProperty.getFormat());
        alipayConfig.setCharset(faceTradeProperty.getCharset());
        alipayConfig.setSignType(faceTradeProperty.getSignType());
        return alipayConfig;
    }

    @Bean
    @ConditionalOnProperty(prefix = "fly.pay.alipay.faceTrade", name = "enabled", value = "true")
    public AlipayClient alipayClient() throws AlipayApiException {
        DefaultAlipayClient client = null;
        try {
            client = new DefaultAlipayClient(getDefaultConfig());
        } catch (AlipayApiException e) {
            log.info("创建配置当面付失败 \n{}", e.getMessage());
            throw e;
        }
        return client;
    }

}
