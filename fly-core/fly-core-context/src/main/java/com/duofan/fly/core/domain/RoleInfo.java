package com.duofan.fly.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * fly 框架用户基本信息
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/15
 */
@Setter
@Getter
@Accessors(chain = true)
public class RoleInfo implements Serializable {

    private String roleNo;


}
