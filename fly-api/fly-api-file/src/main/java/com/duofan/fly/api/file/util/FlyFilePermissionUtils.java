package com.duofan.fly.api.file.util;

import com.duofan.fly.api.file.propterty.FileStorageProperty;
import com.duofan.fly.core.base.constant.log.LogConstant;
import com.duofan.fly.core.base.domain.exception.FlyBizException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
public class FlyFilePermissionUtils {


    @Resource
    private FileStorageProperty property;

//    @Resource
//    private AuthenticationEndpointAnalysis analysis;

    // TODO 文件上传类型校验、文件读写角色校验、大小校验
    public boolean checkPermission(String bucket, String filePathType) {
        Map<String, FileStorageProperty.FlyFilePathTypeConfig> info = property.getFilePathInfo();
        if (info.containsKey(filePathType)) {
            log.info(LogConstant.COMMON_OPERATION_LOG, "文件操作权限判断", "文件路径类型不存在，请检查filePathType是否存在");
            throw new FlyBizException("文件路径类型不存在");
        }
        FileStorageProperty.FlyFilePathTypeConfig config = info.get(filePathType);
        return true;
    }
}
