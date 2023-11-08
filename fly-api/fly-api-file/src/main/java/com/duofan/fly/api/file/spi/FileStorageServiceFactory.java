package com.duofan.fly.api.file.spi;

import com.duofan.fly.core.base.enums.file.FileStorageTypeDic;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileStorageServiceFactory {

    // FileStorageTypeDic
    private final Map<FileStorageTypeDic, FlyFileStorage> storageServiceMap;

    public FileStorageServiceFactory(List<FlyFileStorage> storageServices) {
        storageServiceMap = new HashMap<>();
        for (FlyFileStorage storageService : storageServices) {
            storageServiceMap.put(storageService.getStorageType(), storageService);
        }
    }

    public FlyFileStorage getStorageService(FileStorageTypeDic storageType) {
        return storageServiceMap.get(storageType);
    }
}
