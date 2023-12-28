package com.duofan.fly.manage.generator;

import com.duofan.fly.manage.generator.config.*;
import com.duofan.fly.manage.generator.config.builder.ConfigBuilder;
import com.duofan.fly.manage.generator.config.builder.CustomFile;
import com.duofan.fly.manage.generator.config.rules.DateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.duofan.fly"})
public class GeneratorApp implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApp.class, args);
    }

    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://101.35.55.200:3306/duofan-cms?serverTimezone=Asia/Shanghai", "duofan-cms", "y2mFzPrP2xFLkkjG")
            .build();

    private static final DataSourceConfig START_DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://101.35.55.200:3306/kit-db?serverTimezone=Asia/Shanghai", "kit-db", "mzEM2St3DFsSmeRs")
            .build();

    private static final StrategyConfig GENERATOR_STRATEGY_CONFIG = new StrategyConfig.Builder()
            .addInclude("fly_dict_data", "fly_dict_type")
//            .likeTable(new LikeTable("nav_%"))
            .controllerBuilder()
            .enableFileOverride()
            .serviceBuilder()
            .enableFileOverride()
            .convertServiceFileName((entityName -> entityName + "Service"))
            .build();
    private static final PackageConfig GENERATOR_PACKAGE_CONFIG = new PackageConfig.Builder()
            .parent("com.duofan")
            .moduleName("cms")
            .entity("persistence.entity")
            .service("service")
            .serviceImpl("service.impl")
            .mapper("persistence.mapper")
            .xml("mapper.xml")
            .controller("manage.controller.api.v1.nav")
            .pathInfo(Collections.singletonMap(OutputFile.xml, "/home/duofan/JAVATMP/xml"))
            .build();
    private static final TemplateConfig GENERATOR_TEMPLATE_CONFIG = new TemplateConfig
            .Builder()
            .build();
    private static final GlobalConfig GENERATOR_GLOBAL_CONFIG = new GlobalConfig.Builder()
            .fileOverride()
            .outputDir("C:/home/duofan/JAVATMP")
            .author("duofan")
            .enableSwagger()
            .dateType(DateType.TIME_PACK)
            .commentDate("yyyy-MM-dd")
            .author("duofan")
            .build();

    private final String projectName = "nav";
    private final String requestPackage = "com.duofan.cms.manage.controller.request";

    @Override
    public void run(String... args) throws Exception {
        AutoGenerator generator = new AutoGenerator(START_DATA_SOURCE_CONFIG);

        ConfigBuilder configBuilder = new ConfigBuilder(GENERATOR_PACKAGE_CONFIG, START_DATA_SOURCE_CONFIG,
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
