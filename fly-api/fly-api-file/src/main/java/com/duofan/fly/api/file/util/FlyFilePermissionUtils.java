package com.duofan.fly.api.file.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.duofan.fly.api.file.propterty.FileStorageProperty;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.exception.FlyBizException;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import com.duofan.fly.core.base.enums.file.FileStorageTypeDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Map;

/**
 * 文件权限控制
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/10
 */
@Slf4j
@Component
public class FlyFilePermissionUtils implements ApplicationContextAware {


    private static FileStorageProperty property;

//    @Resource
//    private AuthenticationEndpointAnalysis analysis;

    // TODO 文件上传类型校验、文件读写角色校验、大小校验
    public static void checkUploadPermission(FlyFileMetaData metaData) {
        Map<String, FileStorageProperty.FlyFilePathTypeConfig> info = property.getFilePathInfo();
        if (info.containsKey(metaData.getFileStorageType())) {
            log.info(LogConstant.COMMON_OPERATION_LOG, "文件操作权限判断", "文件路径类型不存在，请检查filePathType【%s】是否存在".formatted(metaData.getFileStorageType()));
            throw new FlyBizException("文件路径类型不存在");
        }
        FileStorageProperty.FlyFilePathTypeConfig config = info.get(metaData.getStoragePathKey());
        if (StrUtil.isNotBlank(config.getPath())) {
            metaData.setFileRelativePath(config.getPath());
        }

        // local存储类型配置下绝对的路径 不带原拓展名
        if (metaData.getStorageTypeDic().equals(FileStorageTypeDict.LOCAL.getCode())) {
            metaData.setFileAbsolutePath(Paths.get(property.getLocal().getUploadRoot(), metaData.getFileRelativePath(), metaData.getFileStorageName()).toString());
        } else {
            FileStorageProperty.OssFileStorageProperties ossProperties = property.getOss().get(metaData.getStorageTypeDic());
            metaData.setFileAbsolutePath(Paths.get(ossProperties.getUploadRoot(), metaData.getFileRelativePath(), metaData.getFileStorageName()).toString());
        }
        if (config.isKeepSuffix()) {
            metaData.setFileAbsolutePath(metaData.getFileAbsolutePath() + StrUtil.DOT + metaData.getFileSuffix());
            metaData.setResourceMapRootUrl(config.getResourceMapRootPrefixUrl());

            metaData.setResourceMapRootUrl(config.getResourceMapRootPrefixUrl());
            String visitFileName = FlyFileUtils.getFileName(metaData.getFileStorageName(), metaData.getFileSuffix());
            // 如果没有设置通过默认方式生成访问路径  nginx映射域名+nginx映射根目录下上传文件的相对路径+文件名
            String mappingAccessUrl = null;
            if (StrUtil.isNotBlank(config.getResourceMapVisitPrefixUrl())) {
                // 访问路径解析
                mappingAccessUrl = FlyFileUtils.getAccessUrl(config.getResourceMapVisitPrefixUrl(), visitFileName);
            } else {
                if (StrUtil.isBlank(config.getResourceMapRootPrefixUrl())) {
                    log.warn("存储类型【{}】，缺少访问映射根路径配置，请检查【{}.{}】配置", metaData.getStoragePathKey(),
                            metaData.getStoragePathKey(), "resource-map-root-prefix-url");
                    throw new FlyBizException("缺少文件映射访问访问根目录配置");
                }
                mappingAccessUrl = FlyFileUtils.getAccessUrl(config.getResourceMapRootPrefixUrl(), metaData.getFileRelativePath(), visitFileName);
            }
            metaData.setResourceMapVisitUrl(mappingAccessUrl);
        }


        if (config.getMaxFileSize() != null && metaData.getFileSize() > config.getMaxFileSize().toBytes()) {
            log.info(LogConstant.COMMON_OPERATION_LOG, "文件上传配置权限判断", "文件大小超过限制【%s】".formatted(FileUtil.readableFileSize(metaData.getFileSize())));
            throw new FlyBizException("文件大小超过限制");
        }

        // 类型判断
        if (config.getAllowTypes() != null && config.getAllowTypes().length > 0) {
            String[] allowTypes = config.getAllowTypes();
            boolean flag = false;
            for (String allowType : allowTypes) {
                if (allowType.equals(metaData.getFileSuffix())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                log.info(LogConstant.COMMON_OPERATION_LOG, "文件上传配置权限判断", "文件类型不允许【%s】".formatted(metaData.getFileContentTypeDesc()));
                throw new FlyBizException("文件类型不允许");
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        property = applicationContext.getBean(FileStorageProperty.class);
    }
}
