package com.duofan.fly.validate.constraintvalidators;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.duofan.fly.core.base.domain.common.FlyDictionary;
import com.duofan.fly.core.storage.FlyDictTypeStorage;
import com.duofan.fly.validate.constraint.Dict;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 身份证验证
 *
 * @author duofan
 * @version 1.0
 * @FlyDict 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/17
 */
@Slf4j
public class DictValidator implements ConstraintValidator<Dict, String> {

    private FlyDictTypeStorage dictService;
    private String dict;

    @Override
    public void initialize(Dict constraintAnnotation) {
        // 获取Spring bean容器里的bean
        dictService = SpringUtil.getBean(FlyDictTypeStorage.class);
        if (dictService == null) {
            log.error("无法获取字典服务");
        }
        this.dict = constraintAnnotation.dict();
        if (StrUtil.isBlank(dict)) {
            log.error("字典类型不能为空");
        }
        
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(value)) {
            return false;
        }
        // 获取注解
        List<FlyDictionary> dictionaries = dictService.getOne(dict);
        for (FlyDictionary dict : dictionaries) {
            if (dict.getValue().contentEquals(value)) {
                return true;
            }
        }
        return false;
    }
}
