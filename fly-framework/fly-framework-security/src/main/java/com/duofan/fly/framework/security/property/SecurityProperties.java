package com.duofan.fly.framework.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "fly.security")
public class SecurityProperties {

    private List<String> noAuthUrls = new ArrayList<>();

    private boolean captchaEnabled = true;

    public String[] getNoAuthUrls() {
        return noAuthUrls.toArray(new String[noAuthUrls.size()]);
    }
}
