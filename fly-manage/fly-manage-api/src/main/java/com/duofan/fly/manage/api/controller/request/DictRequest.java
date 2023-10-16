package com.duofan.fly.manage.api.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * 字典请求
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/16
 */
public class DictRequest {

    @Data
    @Accessors(chain = true)
    public static class Save {
        @NotBlank
        @Length(min = 2, max = 32)
        private String type;
        @Length(min = 1, max = 32)
        @NotBlank
        private String code;
        @NotBlank
        @Length(min = 1, max = 32)
        private String text;
        @Length(max = 32)
        private String remark;
        private long sort;
    }

    @Data
    @Accessors(chain = true)
    public static class Update {
        @NotBlank
        @Length(min = 2, max = 32)
        private String type;
        @Length(min = 1, max = 32)
        @NotBlank
        private String code;
        @NotBlank
        @Length(min = 1, max = 32)
        private String text;
        @Length(max = 32)
        private String remark;
        private long sort;
    }
}
