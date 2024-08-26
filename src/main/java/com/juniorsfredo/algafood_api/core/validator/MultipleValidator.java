package com.juniorsfredo.algafood_api.core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private Integer multipleValue;

    @Override
    public void initialize(Multiple constraintAnnotation) {
       this.multipleValue = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {

        if (value != null) {
            return BigDecimal.valueOf(value.doubleValue()).remainder(BigDecimal.valueOf(multipleValue)).compareTo(BigDecimal.ZERO) == 0;
        }
        return false;
    }
}
