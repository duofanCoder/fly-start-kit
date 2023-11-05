package com.duofan.fly.api.pay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.duofan.fly.api.pay.proptery.AlipayTradeProperty;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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
@EnableConfigurationProperties(AlipayTradeProperty.class)
public class AlipayFaceTradeConfig {

    @Resource
    private AlipayTradeProperty alipayTradeProperty;

    private AlipayConfig getDefaultConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setAppId(alipayTradeProperty.getFaceTrade().getAppId());
        alipayConfig.setPrivateKey(alipayTradeProperty.getFaceTrade().getPrivateKey());
        alipayConfig.setAlipayPublicKey(alipayTradeProperty.getFaceTrade().getPublicKey());
        alipayConfig.setEncryptKey(alipayTradeProperty.getFaceTrade().getEncryptKey());
        alipayConfig.setServerUrl(alipayTradeProperty.getFaceTrade().getServerUrl());
        alipayConfig.setFormat(alipayTradeProperty.getFaceTrade().getFormat());
        alipayConfig.setCharset(alipayTradeProperty.getFaceTrade().getCharset());
        alipayConfig.setSignType(alipayTradeProperty.getFaceTrade().getSignType());
        return alipayConfig;
    }

    @Bean
//    @ConditionalOnProperty(prefix = "fly.pay.alipay.trade-face", name = "enabled", value = "true")
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
