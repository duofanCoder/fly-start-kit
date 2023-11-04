package com.duofan.fly.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
public class UserDto {
    private String id;
    private String newPassword;
    private String rawPassword;
    @JsonDeserialize
    @JsonIgnore
    private String password;
    private String remark;
    private String phone;
    private String username;


    private String gender;
    private String age;
    private String birth;
    private String idCardNo;
    private String email;
    private String avatarImg;
    private String isLocked;
    private String isEnabled;

    private String roleNo;
    private List<String> ownRoleList;
    private String groupNo;
}
