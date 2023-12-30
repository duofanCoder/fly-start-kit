package com.duofan.fly.manage.api.controller.request;

import com.duofan.fly.validate.constraint.IdCardNo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 请求体
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/13
 */
public class UserRequest {


    @Data
    public static class UserUpdate {
        @NotBlank
        @NotNull
        private String username;
        private String gender;
        private String birth;
        @IdCardNo
        private String idCardNo;
        @Email
        private String email;
        private String phone;
        private String avatar;
        private String isLocked;
        private String isEnabled;
        private String roleNo;
        private String remark;
    }

    @Data
    public static class Save {
        @NotBlank
        @NotNull
        private String username;
        private String gender;
        private String birth;
        @IdCardNo
        private String idCardNo;
        @Email
        private String email;
        private String phone;
        private String avatar;
        private String isLocked = "0";
        private String isEnabled = "1";
        private String roleNo;
        private String remark;
    }

    @Data
    public static class PasswdReset {
        private String username;
        private String rawPassword;
        private String newPassword;
    }


    @Data
    public static class UserPage {
        private String id;
        private String username;
        private String gender;
        private String age;
        private String birth;
        private String idCardNo;
        private String email;
        private String phone;
        private String avatar;
        private String isLocked;
        private String isEnabled;
        private String roleNo;
    }

    @Data
    public static class Locked {
        @NotBlank(message = "用户名不能为空")
        private String username;
        @NotBlank(message = "启用状态不能为空")
        private String isLocked;
    }
}
