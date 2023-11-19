package com.duofan.fly.framework.security.property;

import java.util.List;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/19
 */
public class SecurityConst {

    public static final List<String> defaultPermitUrl = List.of("/v3/api-docs/**", "/error/**", "/doc.html", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/favicon.ico", "/logo.svg");
}
