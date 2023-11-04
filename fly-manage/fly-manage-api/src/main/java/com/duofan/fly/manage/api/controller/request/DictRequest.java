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
        @NotBlank
        @Length(min = 2, max = 32)
        private String name;
        @Length(min = 1, max = 32)
        @NotBlank
        private String code;
        private String description;
        @NotBlank
        @Length(min = 1, max = 32)
        private String text;
        private String isEnabled = "1";
        private long sort;
    }

    @Data
    @Accessors(chain = true)
    public static class Enabled {
        @NotBlank
        private String type;
        private String isEnabled = "1";
        private String code;
    }

    @Data
    @Accessors(chain = true)
    public static class Update {

        @NotBlank
        @Length(min = 2, max = 32)
        private String type;
        @NotBlank
        @Length(min = 2, max = 32)
        private String name;
        @Length(min = 1, max = 32)
        @NotBlank
        private String code;
        private String description;
        @NotBlank
        @Length(min = 1, max = 32)
        private String text;
        private String isEnabled = "1";
        private long sort = 0;
    }
}
