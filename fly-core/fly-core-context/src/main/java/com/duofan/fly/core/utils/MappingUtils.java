package com.duofan.fly.core.utils;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.AnnotatedElement;

/**
 * MVC 映射工具类
 *
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2023/11/3
 */
public class MappingUtils {

    public static String getMappingUrl(AnnotatedElement annotationEle) {


        // 获取继承自GetMapping的注解的或其本身的请求路径
        GetMapping getMapping = AnnotationUtils.findAnnotation(annotationEle, GetMapping.class);
        if (getMapping != null) {
            return getMapping.value()[0];
        }
        // 获取继承自PostMapping的注解的或其本身的请求路径
        PostMapping postMapping = AnnotationUtils.findAnnotation(annotationEle, PostMapping.class);
        if (postMapping != null) {
            return postMapping.value()[0];
        }
        // 获取继承自DeleteMapping的注解的或其本身的请求路径
        DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(annotationEle, DeleteMapping.class);
        if (deleteMapping != null) {
            return deleteMapping.value()[0];
        }
        // 获取继承自PutMapping的注解的或其本身的请求路径
        PutMapping putMapping = AnnotationUtils.findAnnotation(annotationEle, PutMapping.class);
        if (putMapping != null) {
            return putMapping.value()[0];
        }
        // 获取继承自PatchMapping的注解的或其本身的请求路径
        PatchMapping patchMapping = AnnotationUtils.findAnnotation(annotationEle, PatchMapping.class);
        if (patchMapping != null) {
            return patchMapping.value()[0];
        }
        // 获取继承自RequestMapping的注解的或其本身的请求路径
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(annotationEle, RequestMapping.class);
        if (requestMapping != null) {
            return requestMapping.value()[0];
        }
        return null;
    }

}
