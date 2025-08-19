package com.example.forum.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class NoSpaceValidator implements ConstraintValidator<NoSpace, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) return false;

        return value.strip().length() > 0;
    }
}
