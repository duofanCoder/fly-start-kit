package com.duofan.fly.core.base.domain.permission;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * jwt token
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/24
 */
@Data
@Accessors(chain = true)
public class FlyToken {
    private String prefix;
    private String token;
    private String expired;
}
