package com.duofan.fly.api.email.service;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.duofan.fly.api.email.config.EmailProperty;
import com.duofan.fly.api.email.utils.EmailTemplateUtil;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.exception.FlyInternalException;
import com.duofan.fly.core.spi.message.VerificationCodeSender;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Slf4j
public class EmailVerificationCodeSender implements VerificationCodeSender {

    private final MailAccount account;
    private final EmailTemplateUtil emailTemplateUtil;

    private final EmailProperty property;

    public EmailVerificationCodeSender(MailAccount account, EmailTemplateUtil emailTemplateUtil, EmailProperty property) {
        this.account = account;
        this.emailTemplateUtil = emailTemplateUtil;
        this.property = property;
    }

    @Override
    public void sendVerificationCode(Set<String> recipients, String template, Map<String, Object> parameters) throws IOException {
        try {
            MailUtil.send(account, recipients, property.getVerificationCodeTopic(),
                    processEmailTemplate(parameters), true);
        } catch (TemplateException e) {
            log.error(LogConstant.COMMON_OPERATION_LOG, "短信发送模板初始化", "初始化失败，请检查模板");
            log.error(e.getMessage());
            throw new FlyInternalException(e);
        } catch (Exception e) {
            log.error(LogConstant.COMMON_OPERATION_LOG, "短信发送", "发送失败，请检查邮件配置");
            log.error(e.getMessage());
            throw new FlyInternalException(e);
        }
    }


    private String processEmailTemplate(Map<String, Object> model) throws IOException, TemplateException {
        return emailTemplateUtil.processEmailTemplate(property.getVerificationCodeTemplate(), model);
    }
}
