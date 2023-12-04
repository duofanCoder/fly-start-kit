package com.duofan.fly.framework.security.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 正则规则工具
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/12/4
 */
@Slf4j
public class SecurityUrlUtils {


    /**
     * 支持pathVariable注解
     *
     * @param url 请求url
     * @return 替换后的url
     */
    public static String pathVariable(String url) {
        return url.replaceAll("\\{\\w+}", "*");
    }

}
