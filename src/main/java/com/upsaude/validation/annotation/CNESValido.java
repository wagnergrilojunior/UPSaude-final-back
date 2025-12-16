package com.upsaude.validation.annotation;

import com.upsaude.validation.validator.CNESValidoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CNESValidoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CNESValido {
    String message() default "CNES inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
