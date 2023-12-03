package com.duofan.fly.api.file.spi;

import com.duofan.fly.core.base.domain.exception.FlyBizException;
import com.duofan.fly.core.base.enums.file.FileStorageTypeDict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class FileStorageServiceFactory {

    // FileStorageTypeDic
    private final Map<FileStorageTypeDict, FlyFileStorage> storageServiceMap;

    public FileStorageServiceFactory(List<FlyFileStorage> storageServices) {
        storageServiceMap = new HashMap<>();
        for (FlyFileStorage storageService : storageServices) {
            storageServiceMap.put(storageService.getStorageType(), storageService);
        }
    }

    public FlyFileStorage getStorageService(FileStorageTypeDict storageType) {
        if (!storageServiceMap.containsKey(storageType)) {
            throw new FlyBizException("不支持的存储类型");
        }
        return storageServiceMap.get(storageType);
    }
}
