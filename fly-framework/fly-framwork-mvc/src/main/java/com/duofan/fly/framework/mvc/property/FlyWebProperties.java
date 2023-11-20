package com.duofan.fly.framework.mvc.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 网站配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/20
 */
@Data
@ConfigurationProperties(prefix = "fly.web")
public class FlyWebProperties {

    // 跨域地址
    private String corsOrigin = "*";
}
