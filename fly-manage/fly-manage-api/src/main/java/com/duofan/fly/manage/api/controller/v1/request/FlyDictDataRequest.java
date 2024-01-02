package com.duofan.fly.manage.api.controller.v1.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FlyDictDataRequest {


    @Data
    public static class SaveOrUpdate {
        private String id;
    }
    @Data
    public static class SwitchIsEnabled {
        @NotBlank
        private String id;
        @NotBlank
        private String isEnabled;
    }
}
