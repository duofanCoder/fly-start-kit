package com.duofan.fly.validate.constraint;

import com.duofan.fly.core.spi.DictExtension;
import com.duofan.fly.validate.constraintvalidators.DictValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 身份证校验
 *
 * @author duofan
 * @version 1.0
 * @IdCardNo 2441051071@qq.com
 * @website duofan.top
 * @date 2023/9/15
 */
@Documented
@Constraint(validatedBy = {DictValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Dict.List.class)
public @interface Dict {
    String message() default "请输入字典值";

    Class<?>[] groups() default {};

    String dict();

    String dictKey() default "";

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Dict[] value();
    }
}
