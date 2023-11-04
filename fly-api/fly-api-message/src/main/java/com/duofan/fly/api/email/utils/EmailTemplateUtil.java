package com.duofan.fly.api.email.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

public class EmailTemplateUtil {

    private final Configuration freemarkerConfig;

    public EmailTemplateUtil(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    public String processEmailTemplate(String templateName, Map<String, Object> model) throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(templateName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
}
