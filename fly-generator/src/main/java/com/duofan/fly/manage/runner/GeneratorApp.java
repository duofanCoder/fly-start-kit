package com.duofan.fly.manage.runner;

import cn.hutool.core.util.StrUtil;
import com.duofan.fly.manage.generator.AutoGenerator;
import com.duofan.fly.manage.generator.config.*;
import com.duofan.fly.manage.generator.config.builder.ConfigBuilder;
import com.duofan.fly.manage.generator.config.builder.CustomFile;
import com.duofan.fly.manage.generator.config.rules.DateType;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.duofan.fly.manage.runner.BlogConfigInfo.*;


public class GeneratorApp {


    static String OUT_PATH = "D:/JAVATMP";

    public static void main(String[] args) throws Exception {
        run(args);
    }

    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder(DATABASE_CONNECT_STR, DATABASE_USERNAME, DATABASE_PASSWORD)
            .build();

    private static StrategyConfig GENERATOR_STRATEGY_CONFIG = null;

    static {
        GENERATOR_STRATEGY_CONFIG = new StrategyConfig.Builder()
                .addInclude(TABLE_NAME)
                .controllerBuilder()
                .enableFileOverride()
                .serviceBuilder()
                .enableFileOverride()
                .convertServiceFileName((entityName -> entityName + "Service"))
                .build();
    }

    private static final PackageConfig GENERATOR_PACKAGE_CONFIG = new PackageConfig.Builder()
            .parent(PARENT_NAME)
            .moduleName(PROJECT_NAME)
            .entity(ENTITY_NAME)
            .service(SERVICE_NAME)
            .serviceImpl(SERVICE_IMPL_NAME)
            .mapper(MAPPER_NAME)
            .xml(MAPPER_XML_NAME)
            .controller(API_CONTROLLER_NAME)
            .pathInfo(Collections.singletonMap(OutputFile.xml, OUT_PATH + "/xml"))
            .build();
    private static final TemplateConfig GENERATOR_TEMPLATE_CONFIG = new TemplateConfig
            .Builder()
            .build();
    private static final GlobalConfig GENERATOR_GLOBAL_CONFIG = new GlobalConfig.Builder()
            .outputDir(OUT_PATH)
            .author("duofan")
            .enableSwagger()
            .dateType(DateType.TIME_PACK)
            .commentDate("yyyy-MM-dd")
            .author("duofan")
            .build();

    private static final String projectName = MODULE_NAME;
    private static final String requestPackage = PACKAGE_NAME + StrUtil.DOT + API_REQUEST_NAME;

    public static void run(String... args) throws Exception {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);

        ConfigBuilder configBuilder = new ConfigBuilder(GENERATOR_PACKAGE_CONFIG, DATA_SOURCE_CONFIG,
                GENERATOR_STRATEGY_CONFIG, GENERATOR_TEMPLATE_CONFIG, GENERATOR_GLOBAL_CONFIG, null);


        configBuilder.setInjectionConfig(new InjectionConfig.Builder()
                .customMap(
                        Map.of(
                                "project", projectName,
                                "requestPackage", requestPackage
                        )
                )
                .customFile(List.of(new CustomFile.Builder()
                                .fileName(".ts")
                                .filePath(GENERATOR_GLOBAL_CONFIG.getOutputDir())
                                .packageName("vue.api.%s".formatted(projectName))
                                .templatePath("templates/.ts.ftl").build(),

                        new CustomFile.Builder()
                                .fileName("/components/%sDialog.vue")
                                .filePath(GENERATOR_GLOBAL_CONFIG.getOutputDir())
                                .packageName("vue.%s".formatted(projectName))
                                .templatePath("templates/Dialog.vue.ftl").build(),
                        new CustomFile.Builder()
                                .fileName("/column.ts")
                                .filePath(GENERATOR_GLOBAL_CONFIG.getOutputDir())
                                .packageName("vue.%s".formatted(projectName))
                                .templatePath("templates/column.ts.ftl").build(),
                        new CustomFile.Builder()
                                .fileName("/index.vue")
                                .filePath(GENERATOR_GLOBAL_CONFIG.getOutputDir())
                                .packageName("vue.%s".formatted(projectName))
                                .templatePath("templates/index.vue.ftl").build(),
                        new CustomFile.Builder()
                                .fileName("Request.java")
                                .filePath(GENERATOR_GLOBAL_CONFIG.getOutputDir())
                                .packageName(requestPackage)
                                .templatePath("templates/request.java.ftl").build()

                ))
                .build());
        generator.config(configBuilder).execute();
    }
}
