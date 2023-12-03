package com.duofan.fly.validate.constraintvalidators;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.duofan.fly.core.base.domain.common.FlyDictionary;
import com.duofan.fly.core.base.entity.FlyDictData;
import com.duofan.fly.core.spi.DictExtension;
import com.duofan.fly.validate.constraint.Dict;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

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
public class DictValidator implements ConstraintValidator<Dict, CharSequence> {

    private DictExtension dictService;

    @Override
    public void initialize(Dict constraintAnnotation) {
        Class<? extends DictExtension> dict = constraintAnnotation.dict();

        // 获取Spring bean容器里的bean
        dictService = SpringUtil.getBean(dict);
        if (dictService == null) {
            log.error("无法获取字典服务: {}", dict.getName());
        }

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(value)) {
            return false;
        }
        for (FlyDictionary dict : dictService.list()) {
            if (dict.getValue().contentEquals(value)) {
                return true;
            }
        }
        return false;
    }
}
