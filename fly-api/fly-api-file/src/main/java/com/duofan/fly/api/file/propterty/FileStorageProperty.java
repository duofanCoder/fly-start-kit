package com.duofan.fly.api.file.propterty;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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

    private Map<String, OssFileStorageProperties> oss = new HashMap<>();


    @Data
    public static class LocalFileStorageProperties {
        // 文件上传的根目录 fly.file-storage.upload-path配置项
        private String uploadPath = "upload";
        // 文件访问的根目录 fly.file-storage.access-path配置项
        private String accessUrl = "img";
    }

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
        private Map<String, String> properties = new HashMap<>();
    }
}
