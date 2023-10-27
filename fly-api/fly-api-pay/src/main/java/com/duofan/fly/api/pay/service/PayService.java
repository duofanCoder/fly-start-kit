package com.duofan.fly.api.pay.service;

import com.duofan.fly.api.pay.dto.TradeDto;

/**
 * 支付抽象类
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/27
 */
public interface PayService {
    TradeDto preCreate(Double price);
}
