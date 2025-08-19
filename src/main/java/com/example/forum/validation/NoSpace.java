package com.example.forum.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NoSpaceValidator.class)

public @interface NoSpace {
    String message() default "投稿内容を入力してください";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
