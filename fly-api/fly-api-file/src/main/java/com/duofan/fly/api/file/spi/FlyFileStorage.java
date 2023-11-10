package com.duofan.fly.api.file.spi;

import com.duofan.fly.core.base.entity.FlyFileMetaData;
import com.duofan.fly.core.base.enums.file.FileStorageTypeDic;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FlyFileStorage {
    FlyFileMetaData store(MultipartFile file, FlyFileMetaData metaData); // 存储文件，返回文件访问URL

    Resource loadFile(FlyFileMetaData fileMetaData); // 加载文件

    void deleteFile(FlyFileMetaData fileMetaData); // 删除文件

    List<FlyFileMetaData> listFiles(); // 获取所有文件列表

    FileStorageTypeDic getStorageType(); // 获取存储类型
}
