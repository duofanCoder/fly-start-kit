package com.duofan.fly.api.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 邮箱配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/4
 */
@Data
@ConfigurationProperties(prefix = "fly.message.email")
public class EmailProperty {
    private boolean enabled;
    private String host = "smtp.qq.com";
    private Integer port = 25;

    private String from;
    private String pass;
    private boolean auth = true;
    private boolean sslEnable = true;
    private String verificationCodeTemplate = "verification-code.ftl";
    private String verificationCodeTopic = "验证码通知";

}
