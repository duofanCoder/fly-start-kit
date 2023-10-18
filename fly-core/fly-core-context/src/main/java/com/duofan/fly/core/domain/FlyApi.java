package com.duofan.fly.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/8
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class FlyApi {
    private String moduleName;
    private String module;
    private String system;
    private String moduleDescription;


    private String opName;
    private String op;
    private String description;
    private boolean isGrantAll;
    private boolean isActivated;
    private String requestUrl;
    private boolean needAuthenticated;

    public FlyApi(String moduleName, String module, String system, String moduleDescription, String opName, String op, String description, boolean isGrantAll, boolean isActivated, String requestUrl, boolean needAuthenticated) {
        this.moduleName = moduleName;
        this.module = module;
        this.system = system;
        this.moduleDescription = moduleDescription;
        this.opName = opName;
        this.op = op;
        this.description = description;
        this.isGrantAll = isGrantAll;
        this.isActivated = isActivated;
        this.requestUrl = requestUrl;
        this.needAuthenticated = needAuthenticated;
    }
}
