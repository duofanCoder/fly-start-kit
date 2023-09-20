package com.duofan.fly.core.base.domain.permission;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 模块操作
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/14
 */
@Data
@Accessors(chain = true)
public class FlyOperation {
    private String roleNo;
    private String op;
}
