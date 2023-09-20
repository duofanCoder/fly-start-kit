package com.duofan.fly.framework.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "fly.security")
public class SecurityProperties {

    private LoginProperties login;

    @Data
    public static class LoginProperties {
        private boolean captchaEnabled = true;

        private String usernameParameter = "username";
        private String passwordParameter = "password";

    }

    private boolean captchaEnabled = true;
}
