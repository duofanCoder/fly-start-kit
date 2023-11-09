package com.duofan.fly.api.file.spi.impl;

import com.duofan.fly.api.file.spi.FileStorageServiceFactory;
import com.duofan.fly.api.file.spi.FlyFileHandler;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import com.duofan.fly.core.storage.FlyFileMetaDataStorage;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/9
 */

public class FlyDefaultFileHandler implements FlyFileHandler {

    private final FlyFileMetaDataStorage storage;
    private final FileStorageServiceFactory fileStorageServiceFactory;

    public FlyDefaultFileHandler(FlyFileMetaDataStorage storage, FileStorageServiceFactory fileStorageServiceFactory) {
        this.storage = storage;
        this.fileStorageServiceFactory = fileStorageServiceFactory;
    }


    @Override
    public FlyFileMetaData upload(MultipartFile file, String storageTypeDic, String filePathType) {

        return null;
    }
}
