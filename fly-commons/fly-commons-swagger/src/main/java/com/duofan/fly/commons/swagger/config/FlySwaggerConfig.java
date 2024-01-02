package com.duofan.fly.commons.swagger.config;

import cn.hutool.core.util.RandomUtil;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.val;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FlySwaggerConfig {
    public static final String HEADER_STRING = "x-access-token";


    /**
     * 根据@Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getTags() != null) {
                openApi.getTags().forEach(tag -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("x-order", RandomUtil.randomInt(0, 100));
                    tag.setExtensions(map);
                });
            }
            if (openApi.getPaths() != null) {
                openApi.addExtension("x-test123", "333");
                openApi.getPaths().addExtension("x-abb", RandomUtil.randomInt(1, 100));
            }

        };
    }

    @Bean
    GroupedOpenApi flyApi() {
        return GroupedOpenApi.builder()
                .group("fly-api-manage")
                .packagesToScan("com.duofan.fly.manage.api.controller")
                .build();
    }
    @Bean
    GroupedOpenApi flyExternalApi() {
        return GroupedOpenApi.builder()
                .group("fly-api-external")
                .packagesToScan("com.duofan.fly.api")
                .build();
    }
//
//    @Bean
//    GroupedOpenApi customer() {
//        return GroupedOpenApi.builder()
//                .group("fly-manage-api")
//                .packagesToScan("com.duofan.fly.manage.api.controller.v1")
//                .build();
//    }

//    @Bean
//    public SpringDocConfigProperties.GroupConfig apiManageApi() {
//        val groupConfig = new SpringDocConfigProperties.GroupConfig();
//        groupConfig.setGroup("fly-manage-api");
//        groupConfig.setPackagesToScan(Collections.singletonList("com.duofan.fly.manage.api.controller.v1"));
//        groupConfig.setDisplayName("内置基本接口");
//        return groupConfig;
//    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("fly - boot 快速开发框架")
                        .version("1.0")
                        .description("swagger增强文档")
                        .termsOfService("http://duofan.top")
                        .license(new License().name("")
                                .url("http://duofan.top")));
    }


}
