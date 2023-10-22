package com.duofan.fly.framework.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "fly.security")
public class SecurityProperties {

    private LoginProperties login = new LoginProperties();

    private TokenProperties token = new TokenProperties();

    private String defaultPassword = "123456";

    @Data
    public static class LoginProperties {
        private boolean captchaEnabled = true;

        private String usernameParameter = "username";
        private String passwordParameter = "password";

    }

    @Data
    public static class TokenProperties {
        private long expired = 60 * 24 * 30;

        // 签名密钥
        private String signSecret = "fly-boot-by-duofan";
        private String algorithm = "HS256";
    }

}
