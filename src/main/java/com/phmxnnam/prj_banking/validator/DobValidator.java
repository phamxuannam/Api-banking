package com.phmxnnam.prj_banking.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = { DobConstraint.class }
)
public @interface DobValidator {

    String message() default "must be at least 18 years old.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min();

}
