package com.duofan.fly.api.file.spi.impl;

import cn.hutool.core.util.StrUtil;
import com.duofan.fly.api.file.object.VO.ResourceVO;
import com.duofan.fly.api.file.propterty.FileStorageProperty;
import com.duofan.fly.api.file.spi.FileStorageServiceFactory;
import com.duofan.fly.api.file.spi.FlyFileHandler;
import com.duofan.fly.api.file.spi.FlyFileStorage;
import com.duofan.fly.api.file.util.FlyFilePermissionUtils;
import com.duofan.fly.api.file.util.FlyFileUtils;
import com.duofan.fly.core.base.domain.exception.FlyBizException;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import com.duofan.fly.core.base.enums.file.FileStorageTypeDict;
import com.duofan.fly.core.storage.FlyFileMetaDataStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

/**
 * 文件服务抽象
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/9
 */

@Slf4j
@AllArgsConstructor
@Getter
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

        FlyFilePermissionUtils.checkUploadPermission(metaData);
        // 上传文件
        try {
            this.uploadFile(file, metaData);
        } catch (Exception e) {
            log.info("文件上传失败:{}", e.getMessage());
            throw new FlyBizException("文件上传失败,请稍后重试");
        }

        // DB操作保存文件信息
        storage.save(metaData);
        return metaData;
    }

    @Override
    public ResourceVO loadFile(String fileUUID) {
        FlyFileMetaData metaData = Optional.ofNullable(storage.getById(fileUUID)).orElseThrow(() -> new FlyBizException("访问失败，文件不存在"));
        // TODO 校验当前文件读权限

        Resource resource = null;

        try {
            // 获取文件操作执行器
            resource = loadFile(metaData);
        } catch (Exception e) {
            log.info("文件读取失败:{}", e.getMessage());
            throw new FlyBizException(e);
        }

        return new ResourceVO(resource, metaData);
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
        FileStorageTypeDict fileStorageType = FileStorageTypeDict.getByCode(storageTypeDic);
        if (fileStorageType == null) {
            log.info("【{}】存储类型方式不支持", storageTypeDic);
            throw new FlyBizException("存储类型方式不支持");
        }
        if (StrUtil.isBlank(filePathType)) {
            throw new FlyBizException("文件路径类型不能为空");
        }
        if (storageTypeDic.equals(FileStorageTypeDict.LOCAL.getCode())) {
            if (StrUtil.isBlank(property.getLocal().getUploadRoot())) {
                throw new FlyBizException("本地文件路径不能为空");
            } else {
                File file = new File(property.getLocal().getUploadRoot());
                if (!file.exists()) {
                    if (!file.mkdir()) {
                        log.info("本地文件路径不存在,文件夹创建失败:【{}】", file.getPath());
                        throw new FlyBizException("本地文件路径不存在");
                    }
                }
            }
        } else {
            if (!property.getOss().containsKey(storageTypeDic)) {
                throw new FlyBizException("OSS存储类型不存在");
            }
        }
    }

    /**
     * 文件合法性校验
     *
     * @param multipartFile 文件
     */
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
     * 构建文件元数据，
     * 配置文件信息存储相关信息在下一步确定
     *
     * @param multipartFile  文件
     * @param storageTypeDic 存储类型 local or oss
     * @param filePathType   文件路径类型 例如：avatar
     * @return
     */
    protected FlyFileMetaData buildFileMetaData(MultipartFile multipartFile, String storageTypeDic, String filePathType) {
        FlyFileMetaData metaData = new FlyFileMetaData();
        metaData.setFileOriginalName(multipartFile.getOriginalFilename());
        // 文件存储的相对路径 设置默认值（实际从配置文件取）
        metaData.setFileRelativePath(filePathType);
        metaData.setStoragePathKey(filePathType);
        metaData.setFileSize(multipartFile.getSize());
        metaData.setFileSuffix(FlyFileUtils.getFileSuffix(multipartFile.getOriginalFilename()));
        metaData.setStorageTypeDic(Objects.requireNonNull(FileStorageTypeDict.getByCode(storageTypeDic)).getCode());
        metaData.setFileStorageName(FlyFileUtils.getUniqueFileName());
        metaData.setFileContentTypeDesc(FlyFileUtils.getFileType(multipartFile.getContentType()));
        metaData.setFileContentType(multipartFile.getContentType());
        metaData.setId(FlyFileUtils.getUUID());
        return metaData;
    }

    /**
     * 获取文件操作执行器
     *
     * @param storageTypeDic
     * @return
     */
    protected FlyFileStorage getFileExecutor(String storageTypeDic) {
        FileStorageTypeDict fileStorageType = FileStorageTypeDict.getByCode(storageTypeDic);

        return fileStorageServiceFactory.getStorageService(fileStorageType);
    }

    /**
     * 上传文件
     *
     * @param multipartFile
     * @param metaData
     * @throws Exception
     */
    protected abstract void uploadFile(MultipartFile multipartFile, FlyFileMetaData metaData) throws Exception;

    protected abstract Resource loadFile(FlyFileMetaData metaData) throws Exception;
}
