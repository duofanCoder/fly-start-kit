package com.duofan.fly.core.base.entity;

import com.duofan.fly.core.base.entity.abstact.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文件元信息
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/8
 */
@Setter
@Getter
@Entity
@Table
@Accessors(chain = true)
public class FlyFileMetaData extends BaseEntity {
    // 单位 字节
    private long fileSize;
    private String storageTypeDic; // 本地(Local) 或 OSS
    private String storagePathKey; // 文件在本地或OSS的存储路径 这里显示类型为配置文件里的key
    private String fileHash; // 文件hash值
    private String accessUrl; // 文件访问路径
    private String fileSuffix; // 文件后缀
    private String fileContentTypeDesc; // 文件类型 （
    private String fileContentType; // 文件类型 （文件上传content-type 内容
    private String fileOriginalName; // 文件原始名称
    private String fileStorageName; // 文件存储名称  （修改后，存在文件夹里文件的名称
    private String fileRelativePath; // 文件存储相对路径
    private String fileAbsolutePath; // 文件存储访问路径
    private String isKeepSuffix;
    private String resourceMapVisitUrl; // 访问路径
    private String resourceMapRootUrl; // 访问路径


    // 一下内容存在自定义的配置内容里面 json存储
    private String fileStorageBucket; // 文件存储桶
    private String fileStorageEndpoint; // 文件存储节点
    private String fileStorageAccessKeyId; // 文件存储访问ID
    private String fileStorageAccessKeySecret; // 文件存储访问密钥
    private String fileStorageType; // 文件存储类型
    private String fileStorageRegion; // 文件存储区域
    private String fileStorageDomain; // 文件存储域名
    private String fileStorageBucketName; // 文件存储桶名称
    private String fileStorageBucketDomain; // 文件存储桶域名
}
