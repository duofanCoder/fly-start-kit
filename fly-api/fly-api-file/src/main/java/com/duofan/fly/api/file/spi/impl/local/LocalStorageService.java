package com.duofan.fly.api.file.spi.impl.local;

import cn.hutool.core.io.FileUtil;
import com.duofan.fly.api.file.propterty.FileStorageProperty;
import com.duofan.fly.api.file.spi.FlyFileStorage;
import com.duofan.fly.api.file.util.FlyFileUtils;
import com.duofan.fly.core.base.domain.exception.FlyBizException;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import com.duofan.fly.core.base.enums.file.FileStorageTypeDict;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        FileStorageProperty.FlyFilePathTypeConfig config = getStorageConfig(metaData.getStoragePathKey());
        if (config == null) {
            log.info("服务端配置【fly.file-storage.file-path-info.{}】文件存储路径配置缺失", metaData.getStoragePathKey());
            throw new FlyBizException("fly.file-storage.file-path-info文件存储路径配置错误");
        }
        try {
            FileUtil.writeFromStream(file.getInputStream(), metaData.getFileAbsolutePath());
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
            String filePath = fileMetaData.getFileAbsolutePath();
            Resource resource = new FileSystemResource(filePath);
//            resource.canRead()
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.info("文件不存在:【{}】", filePath);
                throw new FlyBizException("文件不存在");
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
    public FileStorageTypeDict getStorageType() {
        return FileStorageTypeDict.LOCAL;
    }

}
