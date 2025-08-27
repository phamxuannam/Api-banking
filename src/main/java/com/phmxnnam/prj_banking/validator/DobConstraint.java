package com.phmxnnam.prj_banking.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobConstraint implements ConstraintValidator<DobValidator, LocalDate> {

    private int min;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {

        if(Objects.isNull(value))
            return true;

        long year = ChronoUnit.YEARS.between(value, LocalDate.now());

        return year >= min;
    }

    @Override
    public void initialize(DobValidator constraintAnnotation) {

        min = constraintAnnotation.min();
        ConstraintValidator.super.initialize(constraintAnnotation);

    }
}
