package com.duofan.fly.api.file.propterty;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件存储配置
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/9
 */
@Data
@ConfigurationProperties(prefix = "fly.file-storage")
public class FileStorageProperty {

    private LocalFileStorageProperties local = new LocalFileStorageProperties();

    // 路径类型 => 存储相对路径
    private Map<String, FlyFilePathTypeConfig> filePathInfo = new HashMap<>();
    private Map<String, OssFileStorageProperties> oss = new HashMap<>();


    /**
     * 本地文件存储配置
     */
    @Data
    public static class LocalFileStorageProperties {
        // 文件上传的根目录 fly.file-storage.upload-path配置项
        private String uploadRoot = "/home/duofan/upload";
        // 文件访问的根目录 fly.file-storage.access-root配置项
        private String accessRoot = "img";

    }

    /**
     * 云OSS配置
     */
    @Data
    public static class OssFileStorageProperties {
        private String endpoint;
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
        private String accessUrl;
        private String region;
        private String domain;
        private String path;
        // 文件上传的根目录 fly.file-storage.upload-path配置项
        private String uploadRoot = "/home/duofan/upload";
        // 文件访问的根目录 fly.file-storage.access-root配置项
        private String accessRoot = "img";
        private Map<String, String> properties = new HashMap<>();
    }

    /**
     * 文件上传相对路径 配置
     */
    @Data
    public static class FlyFilePathTypeConfig {
        // 文件存放相对路径     默认用key
        private String path;
        // 最大存储文件大小 单位KB
        private DataSize maxFileSize;
        // 最大存储文件数量
        private Integer maxFileCount;
        // 存储文件类型
        private String[] allowTypes;
        // 权限-写
        private String permissionWrite;
        // 权限-读
        private String permissionRead;
    }
}
