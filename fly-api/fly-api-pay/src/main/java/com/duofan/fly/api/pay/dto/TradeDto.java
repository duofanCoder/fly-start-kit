package com.duofan.fly.api.pay.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author duofaWorker
 * @Email duofancc@qq.com
 * @Date 2023/3/2
 * @Description 阿里当面付交易相关属性
 */
@Data
@Accessors(chain = true)
public class TradeDto {

    private String qrCode;

    private String outTradeNo;

}
