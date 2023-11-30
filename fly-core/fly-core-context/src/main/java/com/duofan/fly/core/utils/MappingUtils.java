package com.duofan.fly.core.utils;

import cn.hutool.core.lang.Tuple;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
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

    public static Tuple getMappingUrl(AnnotatedElement annotationEle) {


        // 获取继承自GetMapping的注解的或其本身的请求路径
        GetMapping getMapping = AnnotationUtils.findAnnotation(annotationEle, GetMapping.class);
        if (getMapping != null) {
            return new Tuple(getMapping.value()[0], HttpMethod.GET);
        }
        // 获取继承自PostMapping的注解的或其本身的请求路径
        PostMapping postMapping = AnnotationUtils.findAnnotation(annotationEle, PostMapping.class);
        if (postMapping != null) {
            return new Tuple(postMapping.value()[0], HttpMethod.POST);
        }
        // 获取继承自DeleteMapping的注解的或其本身的请求路径
        DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(annotationEle, DeleteMapping.class);
        if (deleteMapping != null) {
            return new Tuple(deleteMapping.value()[0], HttpMethod.DELETE);
        }
        // 获取继承自PutMapping的注解的或其本身的请求路径
        PutMapping putMapping = AnnotationUtils.findAnnotation(annotationEle, PutMapping.class);
        if (putMapping != null) {
            return new Tuple(putMapping.value()[0], HttpMethod.PUT);
        }
        // 获取继承自RequestMapping的注解的或其本身的请求路径
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(annotationEle, RequestMapping.class);
        if (requestMapping != null) {
            return new Tuple(requestMapping.value()[0], null);
        }
        return null;
    }

}
