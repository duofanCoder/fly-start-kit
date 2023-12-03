package com.duofan.fly.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 布尔是否字典
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/12/3
 */

@Getter
@AllArgsConstructor
public enum BooleanDict {
    YES("1", "是", "是"),
    NO("0", "否", "否");
    
    private final String code;
    private final String msg;
    private final String description;
}
