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
        private String id;
        private String username;
        private String gender;
        private String age;
        private String birth;
        @IdCardNo
        private String idCardNo;
        @Email
        private String email;
        private String phone;
        private String avatarImg;
        private String isLocked;
        private String isEnabled;
    }

    @Data
    public static class PasswdReset {
        @NotBlank
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
        private String avatarImg;
        private String isLocked;
        private String isEnabled;
    }
}
