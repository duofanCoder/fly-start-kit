package com.duofan.fly.api.pay.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/1
 */
@Getter
@AllArgsConstructor
public enum PayChannelDict {
    ALIPAY_TRADE_FACE("alipay", "当面付", "支付宝当面付"),
    ;
    private final String code;
    private final String msg;
    private final String desc;
}
