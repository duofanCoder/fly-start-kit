package com.duofan.fly.core.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户传输类
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/24
 */

@Data
@Accessors(chain = true)
public class FlyUserDto {
    private String newPassword;
    private String rawPassword;
    private String password;
    private String phone;
    private String username;

}
