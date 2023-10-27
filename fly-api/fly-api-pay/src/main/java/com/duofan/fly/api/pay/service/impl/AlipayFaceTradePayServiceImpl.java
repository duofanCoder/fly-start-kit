package com.duofan.fly.api.pay.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.duofan.fly.api.pay.dto.TradeDto;
import com.duofan.fly.api.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/27
 */
@Slf4j
@Component
public class AlipayFaceTradePayServiceImpl implements PayService {
    @Override
    public TradeDto preCreate(Double price) {
        AlipayClient defaultApiClient = null;
        try {
            defaultApiClient = FaceTradeUtils.getDefaultApiClient();
        } catch (AlipayApiException e) {
            log.info("阿里当面付异常：配置装载出错");
            throw new RuntimeException(e);
        }
        //实例化客户端
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        String outTradeNo = RandomUtil.randomNumbers(32);

        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount("0.01");
        model.setSubject("Iphone6 16G");
        request.setBizModel(model);
        request.setNotifyUrl(AlipayTradeConstants.NotifyUrl);
        AlipayTradePrecreateResponse response = null;
        try {
            response = defaultApiClient.execute(request);
        } catch (AlipayApiException e) {
            log.info("阿里当面付异常：订单Qr预创建失败");
            throw new RuntimeException(e);
        }
        if (response.isSuccess()) {
            log.info("阿里当面付:订单预创建成功, 订单号 = {}", outTradeNo);
            return new AlipayTradeDto().setOutTradeNo(response.getOutTradeNo())
                    .setQrCode(response.getQrCode());
        } else {
            log.info(response.getBody());
            throw new RuntimeException("创建订单失败！");
        }
    }
}
