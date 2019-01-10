package com.restservice.restservicejavateam.utils.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    public void initialize(Username constraintAnnotation) {

    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ("mydevgeek".equalsIgnoreCase(value)) {
            return false;
        }
        return true;
    }
}