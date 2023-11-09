package com.duofan.fly.api.file.controller.api.v1;

import com.duofan.fly.api.file.spi.FlyFileHandler;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * 文件接口
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/8
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/file")
@FlyAccessInfo(moduleName = "文件管理", system = "FLY BOOT")
public class FlyFileController {

    @Resource
    private FlyFileHandler handler;


    /**
     * 上传文件
     *
     * @return
     */
    @PostMapping("/upload")
    @FlyAccessInfo(moduleName = "上传文件", needAuthenticated = false)
    public FlyResult upload(@RequestParam("file") MultipartFile file,
                            @RequestParam("storageTypeDic") String storageTypeDic,
                            @RequestParam("filePathType") String filePathType) {
        FlyFileMetaData metaData = handler.upload(file, storageTypeDic, filePathType);
        return FlyResult.success(metaData);
    }

    @GetMapping("/download")
    @FlyAccessInfo(moduleName = "文件访问", needAuthenticated = false)
    public ResponseEntity<Object> downloadFile(String fileName) throws FileNotFoundException {

        File file = new File(fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment;filename=\"%s", fileName));
        headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt"))
                .body(resource);
    }

    private boolean isAllow(String fileName) {
        String[] allowFiles = {".gif", ".png", ".jpg", ".jpeg", ".bpm", ".svg"};
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        List<String> suffixList = Arrays.stream(allowFiles).toList();
        return suffixList.contains(suffix);
    }
}
