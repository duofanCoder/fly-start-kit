package com.duofan.fly.validate.constraintvalidators;

import cn.hutool.core.util.StrUtil;
import com.duofan.fly.validate.constraint.IdCardNo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证验证
 *
 * @author duofan
 * @version 1.0
 * @IdCardNo 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/17
 */
public class IdCardNoValidator implements ConstraintValidator<IdCardNo, CharSequence> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private String idCardNo;
    private static final Pattern LOCAL_PART_PATTERN = Pattern.compile("^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");

    @Override
    public void initialize(IdCardNo constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (StrUtil.isBlank(value)) {
            return true;
        }
        // 判断最后一位为x
        String stringValue = value.toString().toUpperCase();
        int splitPosition = stringValue.lastIndexOf("X");

        if (splitPosition != -1 && splitPosition != (stringValue.length() - 1)) {
            return false;
        }

        String idCardNo = splitPosition != -1 ? stringValue.substring(0, splitPosition) : stringValue;
        Matcher m = LOCAL_PART_PATTERN.matcher(idCardNo);
        return m.matches();
    }
}
