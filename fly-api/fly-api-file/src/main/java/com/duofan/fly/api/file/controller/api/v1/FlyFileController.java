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

    @GetMapping("/download/{fileName}")
    @FlyAccessInfo(moduleName = "文件访问", needAuthenticated = false)
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName) {
        ResourceVO vo = handler.loadFile(fileName);
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType(vo.getMetaData().getFileContentType());
        // TODO EXCEL 下载没有返回对应文件名称
        headers.setContentType(mediaType);
        if (!mediaType.equals(MediaType.IMAGE_JPEG) &&
                !mediaType.equals(MediaType.IMAGE_PNG) &&
                !mediaType.equals(MediaType.IMAGE_GIF)) {
            headers.setContentDispositionFormData("attachment", vo.getMetaData().getFileOriginalName());
        }


        return ResponseEntity.ok()
                .headers(headers)
                .body(vo.getResource());
    }

    private boolean isAllow(String fileName) {
        String[] allowFiles = {".gif", ".png", ".jpg", ".jpeg", ".bpm", ".svg"};
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        List<String> suffixList = Arrays.stream(allowFiles).toList();
        return suffixList.contains(suffix);
    }
}
