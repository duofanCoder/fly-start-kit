package com.duofan.fly.manage.api.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 短信发送
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/4
 */

public class MessageRequest {
    @Data
    public static class SendTo {
        @NotBlank
        @NotNull
        private String to;
        private String name;
    }

}
