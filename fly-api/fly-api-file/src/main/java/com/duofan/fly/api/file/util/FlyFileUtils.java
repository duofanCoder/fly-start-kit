package com.duofan.fly.api.file.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;

import java.nio.file.Paths;
import java.time.LocalDate;

/**
 * 文件工具类
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/10
 */
public class FlyFileUtils {


    public static String getFileSuffix(String fileName) {
        return FileUtil.getSuffix(fileName);
    }

    /**
     * 根据mine类型，返回文件类型
     *
     * @param contentType 类型
     * @author zuihou
     * @date 2019-05-06 13:41
     */
    public static String getFileType(String contentType) {
        if (contentType == null) {
            return null;
        }
        if (contentType.contains("image")) {
            return "图片";
        }
        if (contentType.contains("video")) {
            return "视频";
        }
        if (contentType.contains("audio")) {
            return "音频";
        }
        if (contentType.contains("pdf")) {
            return "pdf";
        }
        if (contentType.contains("word")) {
            return "word";
        }
        if (contentType.contains("spreadsheetml")) {
            return "excel";
        }
        if (contentType.contains("presentationml")) {
            return "ppt";
        }
        return "其他";
    }

    public static String getFilePath(String filePathType) {
        return Paths.get(filePathType, LocalDate.now().toString()).toString();
    }

    public static String getUniqueFileName() {
        return RandomUtil.randomString(128);
    }

    public static String getUUID() {
        return RandomUtil.randomString(32);
    }

    public static String getAccessUrl(String accessRoot, String... more) {
        return accessRoot + "/" + Paths.get("", more).toString();
    }
}
