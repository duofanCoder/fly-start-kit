package com.duofan.fly.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class FlyModule {
    private String moduleName;
    private String module;
    private String system;
    private String description;
    // key => method name, value => api info
    private Map<String, FlyApi> apis;
}

