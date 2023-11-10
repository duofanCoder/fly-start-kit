package com.duofan.fly.api.file.spi.impl;

import cn.hutool.core.util.StrUtil;
import com.duofan.fly.api.file.propterty.FileStorageProperty;
import com.duofan.fly.api.file.spi.FileStorageServiceFactory;
import com.duofan.fly.api.file.spi.FlyFileHandler;
import com.duofan.fly.api.file.util.FlyFileUtils;
import com.duofan.fly.core.base.domain.exception.FlyBizException;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import com.duofan.fly.core.base.enums.file.FileStorageTypeDic;
import com.duofan.fly.core.storage.FlyFileMetaDataStorage;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

/**
 * 文件服务抽象
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/9
 */

@AllArgsConstructor
public abstract class AbstractFileHandler implements FlyFileHandler {

    private final FlyFileMetaDataStorage storage;
    private final FileStorageServiceFactory fileStorageServiceFactory;
    private final FileStorageProperty property;


    /**
     * 上传操作
     *
     * @param file
     * @param storageTypeDic
     * @param filePathType
     * @return
     */
    @Override
    public FlyFileMetaData upload(MultipartFile file, String storageTypeDic, String filePathType) {
        validateFile(file, storageTypeDic, filePathType);
        // TODO 校验当前文件写权限
        FlyFileMetaData metaData = buildFileMetaData(file, storageTypeDic, filePathType);

        FileStorageTypeDic fileStorageType = FileStorageTypeDic.getByCode(storageTypeDic);

        // 上传文件
        fileStorageServiceFactory.getStorageService(fileStorageType)
                .store(file, metaData);
        // DB操作保存文件信息
        storage.save(metaData);
        return metaData;
    }

    /**
     * 校验文件
     *
     * @param multipartFile
     * @param storageTypeDic
     * @param filePathType
     */
    protected void validateFile(MultipartFile multipartFile, String storageTypeDic, String filePathType) {
        validateFile(multipartFile);
        if (StrUtil.isBlank(storageTypeDic)) {
            throw new FlyBizException("存储类型不能为空");
        }
        FileStorageTypeDic fileStorageType = FileStorageTypeDic.getByCode(storageTypeDic);
        if (fileStorageType == null) {
            throw new FlyBizException("存储类型不存在");
        }
        if (StrUtil.isBlank(filePathType)) {
            throw new FlyBizException("文件路径类型不能为空");
        }
        if (storageTypeDic.equals("local")) {
            if (StrUtil.isBlank(property.getLocal().getUploadRoot())) {
                throw new FlyBizException("本地文件路径不能为空");
            } else {
                File file = new File(property.getLocal().getUploadRoot());
                if (!file.exists()) {
                    throw new FlyBizException("本地文件路径不存在");
                }
            }
        } else {
            if (!property.getOss().containsKey(storageTypeDic)) {
                throw new FlyBizException("OSS存储类型不存在");
            }
        }
    }

    private void validateFile(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new FlyBizException("文件不能为空");
        }
        if (multipartFile.isEmpty()) {
            throw new FlyBizException("文件不能为空");
        }
        if (StrUtil.isBlank(multipartFile.getOriginalFilename())) {
            throw new FlyBizException("文件名不能为空");
        }
        if (!StrUtil.contains(multipartFile.getOriginalFilename(), ".")) {
            throw new FlyBizException("文件缺少后缀名");
        }

    }


    /**
     * 构建文件元数据
     *
     * @param multipartFile
     * @param storageTypeDic
     * @param filePathType
     * @return
     */
    protected FlyFileMetaData buildFileMetaData(MultipartFile multipartFile, String storageTypeDic, String filePathType) {
        FlyFileMetaData metaData = new FlyFileMetaData();
        metaData.setFileOriginalName(multipartFile.getOriginalFilename());
        metaData.setFileStoragePath(filePathType);
        metaData.setFileSize(multipartFile.getSize());
        metaData.setFileSuffix(FlyFileUtils.getFileSuffix(multipartFile.getOriginalFilename()));
        metaData.setFileStorageType(Objects.requireNonNull(FileStorageTypeDic.getByCode(storageTypeDic)).getCode());
        metaData.setFileStorageName(FlyFileUtils.getUniqueFileName());
        metaData.setFileContentType(FlyFileUtils.getFileType(multipartFile.getContentType()));
        metaData.setId(FlyFileUtils.getUUID());
        return metaData;
    }

    protected abstract void uploadFile(MultipartFile multipartFile, FlyFileMetaData metaData) throws Exception;
}
