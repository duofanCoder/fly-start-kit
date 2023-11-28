package com.duofan.fly.manage.generator.engine;

import com.duofan.fly.manage.generator.config.ConstVal;
import com.duofan.fly.manage.generator.config.builder.ConfigBuilder;
import com.jfinal.template.Engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * enjoy 模板引擎实现文件输出
 *
 * @author flyinke
 * @since 2022-06-16
 */
public class EnjoyTemplateEngine extends AbstractTemplateEngine {

    private Engine engine;

    @Override
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        engine = Engine.createIfAbsent("mybatis-plus-generator", e -> {
            e.setToClassPathSourceFactory();
        });
        return this;
    }

    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, File outputFile) throws Exception {
        String str = engine.getTemplate(templatePath).renderToString(objectMap);
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             OutputStreamWriter ow = new OutputStreamWriter(fos, ConstVal.UTF8);
             BufferedWriter writer = new BufferedWriter(ow)) {
            writer.append(str);
        }
        LOGGER.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }

    @Override
    public String templateFilePath(String filePath) {
        final String dotVm = ".ej";
        return filePath.endsWith(dotVm) ? filePath : filePath + dotVm;
    }
}

