package com.duofan.fly.framework.security.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "fly.security")
public class SecurityProperties {

    private List<String> noAuthUrls = new ArrayList<>();

    public String[] getNoAuthUrls() {
        return noAuthUrls.toArray(new String[noAuthUrls.size()]);
    }
}
