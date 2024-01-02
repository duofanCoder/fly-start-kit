package com.duofan.fly.manage.api.controller.v1.request;

import com.duofan.fly.validate.constraint.Dict;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FlyDictTypeRequest {


    @Data
    public static class SaveOrUpdate {
        private String id;
    }
    @Data
    public static class SwitchStatus {
        @NotBlank
        private String id;
        @NotBlank
        @Dict(dict = "booleanDic", message = "字典类型不存在")
        private String isEnabled;
    }
}
