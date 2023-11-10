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

        Map<String, FileStorageProperty.FlyFilePathTypeConfig> info = property.getFilePathInfo();
        FileStorageProperty.FlyFilePathTypeConfig config = info.get(metaData.getStoragePath());

        String absolutePath = Paths.get(local.getUploadRoot(), config.getPath(), metaData.getFileStorageName()).toString();
        try {
            FileUtil.writeFromStream(file.getInputStream(), absolutePath);
        } catch (IOException e) {
            throw new FlyBizException("文件写入失败");
        }
        metaData.setAccessUrl(FlyFileUtils.getAccessUrl(local.getAccessRoot(), metaData.getId()));

        return metaData;
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
