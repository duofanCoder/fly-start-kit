package com.duofan.fly.core.dto;

import com.duofan.fly.core.base.entity.FlyRole;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/10
 */
@Data
@Accessors(chain = true)
public class AdminLoginInfo implements Serializable {
    private String username;
    private FlyRole role;
    private List<FlyRole> roleList;
    private Set<String> authorityList;
}
