package com.duofan.fly.api.file.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.duofan.fly.api.file.propterty.FileStorageProperty;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.exception.FlyBizException;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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
            metaData.setFileStoragePath(config.getPath());
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
