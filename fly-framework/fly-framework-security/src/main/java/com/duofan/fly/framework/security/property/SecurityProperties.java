package com.duofan.fly.framework.security.property;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "fly.security")
public class SecurityProperties {

    private Filter filter;

    @Data
    public static class Filter {
        private List<String> authUrls;

        private List<String> noAuthUrls;

    }
}
