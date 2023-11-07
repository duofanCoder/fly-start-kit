package com.duofan.fly.framework.jpa.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * fly jpa配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/7
 */
@Data
@ConfigurationProperties(prefix = "fly.jpa")
public class FlyJpaProperty {

    private boolean enabled = true;
    // 扫描的包数组
    private String[] scanPackages = new String[]{""};
}
