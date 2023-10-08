package com.duofan.fly.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/8
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class FlyApi {
    private String opName;
    private String op;
    private String description;
    private boolean isGrantAll;
    private boolean isOwn;
}
