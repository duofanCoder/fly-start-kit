package com.duofan.fly.api.pay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.duofan.fly.api.pay.utils.AlipayTradeProperty;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 当面副
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/27
 */
@ConditionalOnProperty(prefix = "fly.pay.alipay", name = "faceTrade", matchIfMissing = true)
@EnableConfigurationProperties(AlipayTradeProperty.class)
public class AlipayFaceTradeConfig {

    @Resource
    private AlipayTradeProperty alipayTradeProperty;

    private AlipayConfig getDefaultConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();

        alipayConfig.setAppId(AlipayTradeConstants.AppId);
        alipayConfig.setPrivateKey(AlipayTradeConstants.PrivateKey);
        alipayConfig.setAlipayPublicKey(AlipayTradeConstants.PublicKey);
        alipayConfig.setEncryptKey(AlipayTradeConstants.EncryptKey);
//        alipayConfig.setServerUrl(AlipayTradeConstants.ServerUrl);
//        alipayConfig.setFormat(AlipayTradeConstants.Format);
//        alipayConfig.setCharset(AlipayTradeConstants.Charset);
//        alipayConfig.setSignType(AlipayTradeConstants.SignType);
        return alipayConfig;
    }

    public static AlipayClient getDefaultApiClient() throws AlipayApiException {
        DefaultAlipayClient client = null;
        try {
            client = new DefaultAlipayClient(getDefaultConfig());
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

}
