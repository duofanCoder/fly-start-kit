package com.duofan.fly.api.file.spi.impl;

import com.duofan.fly.api.file.spi.FlyFileStorage;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import com.duofan.fly.core.base.enums.file.FileStorageTypeDic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class LocalStorageService implements FlyFileStorage {

    @Value("${fly.file-storage.local.upload-path}")
    private String uploadPath;

    @Override
    public String store(MultipartFile file) {
        return null;
    }

    @Override
    public Resource loadFile(FlyFileMetaData fileMetaData) {
        return null;
    }

    @Override
    public void deleteFile(FlyFileMetaData fileMetaData) {

    }

    @Override
    public List<FlyFileMetaData> listFiles() {
        return null;
    }

    @Override
    public FileStorageTypeDic getStorageType() {
        return FileStorageTypeDic.LOCAL;
    }
}
