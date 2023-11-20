package com.duofan.fly.api.file.spi.impl.local;

import cn.hutool.core.io.FileUtil;
import com.duofan.fly.api.file.propterty.FileStorageProperty;
import com.duofan.fly.api.file.spi.FlyFileStorage;
import com.duofan.fly.api.file.util.FlyFileUtils;
import com.duofan.fly.core.base.domain.exception.FlyBizException;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import com.duofan.fly.core.base.enums.file.FileStorageTypeDic;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class LocalStorageService implements FlyFileStorage {

    private final FileStorageProperty property;

    /**
     * 存储文件到本地
     *
     * @param file
     * @param metaData
     * @return
     */
    @Override
    public FlyFileMetaData store(MultipartFile file, FlyFileMetaData metaData) {
        FileStorageProperty.LocalFileStorageProperties local = property.getLocal();

        FileStorageProperty.FlyFilePathTypeConfig config = getStorageConfig(metaData.getStoragePath());
        if (config == null) {
            log.info("服务端配置【fly.file-storage.file-path-info.{}】文件存储路径配置缺失", metaData.getStoragePath());
            throw new FlyBizException("fly.file-storage.file-path-info文件存储路径配置错误");
        }
        String absolutePath = Paths.get(local.getUploadRoot(), config.getPath(), metaData.getFileStorageName()).toString();
        try {
            FileUtil.writeFromStream(file.getInputStream(), absolutePath);
        } catch (IOException e) {
            throw new FlyBizException("文件写入失败");
        }
        metaData.setAccessUrl(FlyFileUtils.getAccessUrl(local.getAccessRoot(), metaData.getId()));

        return metaData;
    }

    private FileStorageProperty.FlyFilePathTypeConfig getStorageConfig(String storagePath) {
        Map<String, FileStorageProperty.FlyFilePathTypeConfig> info = property.getFilePathInfo();
        return info.get(storagePath);
    }

    @Override
    public Resource loadFile(FlyFileMetaData fileMetaData) {
        try {
            FileStorageProperty.LocalFileStorageProperties local = property.getLocal();

            FileStorageProperty.FlyFilePathTypeConfig config = getStorageConfig(fileMetaData.getStoragePath());
            String filePath = Paths.get(local.getUploadRoot(), config.getPath(), fileMetaData.getFileStorageName()).toString();
            Resource resource = new FileSystemResource(filePath);
//            resource.canRead()
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FlyBizException("Could not read file: " + fileMetaData.getFileStorageName());
            }
        } catch (Exception e) {
            throw new FlyBizException("文件读取失败");
        }
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
