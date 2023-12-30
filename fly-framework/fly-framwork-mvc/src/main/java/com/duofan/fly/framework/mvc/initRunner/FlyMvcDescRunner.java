package com.duofan.fly.framework.mvc.initRunner;

import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.framework.mvc.property.FlyWebProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/12/30
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FlyMvcDescRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        corsTip();
    }

    @Resource
    private FlyWebProperties properties;
    @Resource
    private Environment env;

    private void corsTip() {
        String profilesAct = env.getProperty("spring.profiles.active");
        if (profilesAct != null && profilesAct.equals("prod") && "*".equals(properties.getCorsOrigin())) {
            log.warn(LogConstant.COMPONENT_LOG, "MVC配置", "跨域已关闭【生产环境请务必开启跨域】");
        } else {
            if ("*".equals(properties.getCorsOrigin())) {
                log.info(LogConstant.COMPONENT_LOG, "MVC配置", "跨域已关闭【生产环境请务必开启跨域】");
            } else {
                log.info(LogConstant.COMPONENT_LOG, "MVC配置", "跨域已开启【生产环境请务必开启跨域】");
            }
        }
    }


}
