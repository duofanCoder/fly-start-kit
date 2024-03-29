package com.duofan.fly.framework.mvc.config;

import com.duofan.fly.framework.mvc.property.FlyWebProperties;
import com.duofan.fly.framework.security.context.lock.DebounceRequestLockoutFilter;
import com.duofan.fly.framework.security.property.SecurityProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 配置MVC， WebMvcConfigurer实现和knife4j 冲突
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/10/9
 */
@Slf4j
@Configuration
@EnableWebMvc
@EnableConfigurationProperties(FlyWebProperties.class)
public class FlyMvcConfig implements WebMvcConfigurer {

    @Resource
    private FlyWebProperties properties;
    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private DebounceRequestLockoutFilter debounceRequestLockoutFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (securityProperties.getFilter().isDebounceRequestLockoutEnabled()) {
            registry.addInterceptor(debounceRequestLockoutFilter);
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(properties.getCorsOrigin())
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * 返前端，序列化 配置
     *
     * @return
     */
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(MappingJackson2HttpMessageConverter.class::isInstance);
        converters.add(mappingJackson2HttpMessageConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/markdown/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
