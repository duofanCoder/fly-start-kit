package com.duofan.fly.api.file.spi;

import com.duofan.fly.api.file.object.VO.ResourceVO;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件处理
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/9
 */
public interface FlyFileHandler {
    FlyFileMetaData upload(MultipartFile file, String storageTypeDic, String filePathType);

    ResourceVO loadFile(String fileUUID);

    void deleteFile(String fileUUID);
}
