package com.duofan.fly.validate.constraintvalidators;

import com.duofan.fly.validate.constraint.IdCardNo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

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
    private java.util.regex.Pattern pattern;

    @Override
    public void initialize(IdCardNo constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        // we only apply the regexp if there is one to apply
        if (!".*".equals(constraintAnnotation.regexp())) {
            try {
                pattern = java.util.regex.Pattern.compile(constraintAnnotation.regexp());
            } catch (PatternSyntaxException e) {
                throw LOG.getInvalidRegularExpressionException(e);
            }
        }
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }

        // 判断最后一位为x
        String stringValue = value.toString();
        int splitPosition = stringValue.lastIndexOf('x');

        if (splitPosition != (stringValue.length() - 1)) {
            return false;
        }

        String idCardNo = splitPosition != -1 ? stringValue.substring(0, splitPosition) : stringValue;
        Matcher m = pattern.matcher(idCardNo);
        return m.matches();
    }
}
