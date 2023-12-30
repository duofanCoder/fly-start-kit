package com.duofan.fly.framework.mvc.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * 工具类
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/12/30
 */
@Slf4j
public class FlyMvcRegisterUtils {

    private static void corsOriginRegistry(CorsRegistry registry, String corsOrigin) {


        log.info("MVC配置", "跨域已关闭【生产环境开启跨域】");
        registry.addMapping("/**")
                .allowedOriginPatterns(corsOrigin);
    }

}
