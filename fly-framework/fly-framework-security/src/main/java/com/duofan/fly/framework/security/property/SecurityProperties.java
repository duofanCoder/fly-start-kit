package com.duofan.fly.framework.security.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "fly.security")
public class SecurityProperties {

    private LoginProperties login = new LoginProperties();

    private TokenProperties token = new TokenProperties();

    private SecurityFilterProperties filter = new SecurityFilterProperties();

    private String defaultPassword = "123456";

    /**
     * spring security 允许访问、监控允许访问的url
     * 一般配置非controller映射的接口、静态资源等
     */
    @Value("${fly.security.permit-url:}")
    private List<String> permitUrl;

    @Data
    public static class LoginProperties {
        private boolean captchaEnabled = true;
        private boolean errorCountEnabled = true;

        private String usernameParameter = "username";
        private String passwordParameter = "password";
        private String isRememberParameter = "isRemember";

    }

    @Data
    public static class SecurityFilterProperties {
        // 配置文件malicious-request-lockout.enabled
        @Value("${fly.security.filter.debounce-request-lockout.enabled:false}")
        private boolean debounceRequestLockoutEnabled;

        // 配置文件malicious-request-lockout.ignored-url
        @Value("${fly.security.filter.debounce-request-lockout.ignored-url:}")
        private List<String> debounceRequestLockoutIgnoredUrl = List.of("/api/v1/dict/list");
        

    }

    @Data
    public static class TokenProperties {
        @DurationUnit(ChronoUnit.HOURS)
        private Duration expired = Duration.ofHours(24);
        // 签名密钥
        private String signSecret = "fly-boot-by-duofan";
        private String algorithm = "HS256";
    }

}
