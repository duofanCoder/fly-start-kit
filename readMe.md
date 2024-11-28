# 项目介绍

基于springboot 3 + mybatis-plus + java 21 版本 封装脚手架

## 支持
* 代码生成
* 全局异常处理
* redis
* jwt
* jpa 实体自动生成表
* RBAC权限配置
* knife增强版swagger 接口文档
* 全局日志处理
* 短信平台
* 接口字典拦截
* 后台管理
  ...

编写规范

前端访问必须要有jessionId

布尔值使用 isVisible is{}

字典值 使用typeDic {}DIc

白名单地址禁止使用{} @pathVariable 获取url参数将无效，需要手动配置 fly.security.permit-url:

# TODO

1. 模块分析：相同接口地址，报错问题
