package com.duofan.fly.api.email.config;

import cn.hutool.extra.mail.MailAccount;
import com.duofan.fly.api.email.service.EmailVerificationCodeSender;
import com.duofan.fly.api.email.utils.EmailTemplateUtil;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.TemplateExceptionHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 邮箱系统配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/4
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(EmailProperty.class)
public class FlyMessageConfig {
    @Resource
    private EmailProperty emailProperty;

    @Bean
    @ConditionalOnProperty(name = "fly.message.email.enabled", havingValue = "true")
    @ConditionalOnMissingBean(EmailVerificationCodeSender.class)
    EmailVerificationCodeSender verificationCodeSender() {
        MailAccount account = new MailAccount();
        {
            account.setHost(emailProperty.getHost());
            account.setPort(emailProperty.getPort());
            account.setAuth(emailProperty.isAuth());
            account.setFrom(emailProperty.getFrom());
            account.setPass(emailProperty.getPass());
            account.setSslEnable(emailProperty.isSslEnable());
        }
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_30);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 设置模板加载器为基于类路径的加载器
        cfg.setTemplateLoader(new ClassTemplateLoader(EmailTemplateUtil.class, "/"));

        return new EmailVerificationCodeSender(account, new EmailTemplateUtil(cfg), emailProperty);
    }
}
