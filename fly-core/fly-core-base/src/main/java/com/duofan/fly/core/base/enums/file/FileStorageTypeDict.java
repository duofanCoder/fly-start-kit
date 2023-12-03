package com.duofan.fly.core.base.enums.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件存储类型枚举
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/8
 */
@Getter
@AllArgsConstructor
public enum FileStorageTypeDict {

    /**
     * 本地存储
     */
    LOCAL("local", "本地存储", "本地存储"),
    /**
     * 阿里云OSS
     */
    ALIYUN_OSS("aliyun_oss", "阿里云OSS", "阿里云OSS"),
    /**
     * 腾讯云COS
     */
    TENCENT_COS("tencent_cos", "腾讯云COS", "腾讯云COS"),
    /**
     * 七牛云KODO
     */
    QINIU_KODO("qiniu_kodo", "七牛云KODO", "七牛云KODO"),
    /**
     * 百度云BOS
     */
    BAIDU_BOS("baidu_bos", "百度云BOS", "百度云BOS"),
    /**
     * 华为云OBS
     */
    HUAWEI_OBS("huawei_obs", "华为云OBS", "华为云OBS"),
    /**
     * 京东云OSS
     */
    JD_OSS("jd_oss", "京东云OSS", "京东云OSS");

    private final String code;
    private final String msg;
    private final String desc;

    public static FileStorageTypeDict getByCode(String code) {
        for (FileStorageTypeDict value : FileStorageTypeDict.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
