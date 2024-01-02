package com.duofan.fly.manage.runner;

import java.util.List;

public class BlogConfigInfo {


    public final static List<String> TABLE_NAME = List.of("biz_picture_album");

    public final static boolean isLike = false;
    public final static String TABLE_LIKE_NAME = "";


    // 父路径
    public final static String PARENT_NAME = "com.duofan";
    // 项目名称
    public final static String PROJECT_NAME = "cms";
    public final static String MODULE_NAME = "cms";
    // 包名称
    public final static String PACKAGE_NAME = "com.duofan.cms";
    public final static String ENTITY_NAME = "persistence.entity";
    // 模块名称
    public final static String SERVICE_NAME = "service";
    public final static String SERVICE_IMPL_NAME = "service.impl";
    public final static String MAPPER_NAME = "persistence.mapper";
    public final static String MAPPER_XML_NAME = "mapper.xml";
    public final static String API_CONTROLLER_NAME = "manage.controller.v1.api.blog";
    public final static String API_REQUEST_NAME = "manage.controller.v1.request";

    public final static String DATABASE_CONNECT_STR = "jdbc:mysql://101.35.55.200:3306/duofan-cms?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false";
    public final static String DATABASE_USERNAME = "duofan-cms";
    public final static String DATABASE_PASSWORD = "y2mFzPrP2xFLkkjG";

}
