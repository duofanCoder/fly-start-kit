package com.duofan.fly.api.file.controller.api.v1;

import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.storage.FlyFileMetaDataStorage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/8
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/file")
@FlyAccessInfo(moduleName = "文件管理", system = "FLY BOOT")
public class FlyFileController {

    @Resource
    private FlyFileMetaDataStorage storage;

    
}
