package com.duofan.fly.api.file.spi.impl;

import com.duofan.fly.api.file.propterty.FileStorageProperty;
import com.duofan.fly.api.file.spi.FileStorageServiceFactory;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import com.duofan.fly.core.storage.FlyFileMetaDataStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * 默认处理文件
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/10
 */
@Slf4j
public class FlyDefaultFileHandler extends AbstractFileHandler {
    public FlyDefaultFileHandler(FlyFileMetaDataStorage storage, FileStorageServiceFactory fileStorageServiceFactory, FileStorageProperty property) {
        super(storage, fileStorageServiceFactory, property);
    }

    @Override
    protected void uploadFile(MultipartFile multipartFile, FlyFileMetaData metaData) throws Exception {
        super.getFileExecutor(metaData.getStorageTypeDic())
                .store(multipartFile, metaData);
    }

    @Override
    protected Resource loadFile(FlyFileMetaData metaData) throws Exception {
        return super.getFileExecutor(metaData.getStorageTypeDic())
                .loadFile(metaData);
    }

    @Override
    public void deleteFile(String fileUUID) {
        
    }
}
