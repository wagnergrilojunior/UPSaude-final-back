package com.upsaude.validation.annotation;

import com.upsaude.validation.validator.CelularValidoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CelularValidoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CelularValido {
    String message() default "{validation.celular.invalido}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
