package com.duofan.fly.api.file.controller.api.v1;

import com.duofan.fly.api.file.object.VO.ResourceVO;
import com.duofan.fly.api.file.spi.FlyFileHandler;
import com.duofan.fly.core.base.domain.common.FlyResult;
import com.duofan.fly.core.base.domain.permission.access.FlyAccessInfo;
import com.duofan.fly.core.base.entity.FlyFileMetaData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
     * @param file 文件
     * @param storageTypeDic 存储类型 本地(Local) 或 OSS
     * @param filePathType 文件路径类型 配置文件存储key
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

    @GetMapping("/download/{fileName}")
    @FlyAccessInfo(moduleName = "文件访问", needAuthenticated = false, isDebounce = false)
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName) throws UnsupportedEncodingException {
        ResourceVO vo = handler.loadFile(fileName);
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType(vo.getMetaData().getFileContentType());
        headers.setContentType(mediaType);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(vo.getMetaData()
                .getFileOriginalName(), StandardCharsets.UTF_8));
        return ResponseEntity.ok()
                .headers(headers)
                .body(vo.getResource());
    }

}
