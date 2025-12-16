package com.upsaude.validation.annotation;

import com.upsaude.validation.validator.IntervaloDataValidoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Valida regra simples: fim >= início (quando ambos existirem).
 * Reutilizável para LocalDate/OffsetDateTime/Instant (comparação via Comparable).
 *
 * Observação: não impõe obrigatoriedade; use @NotNull/@NotBlank conforme necessário.
 */
@Documented
@Constraint(validatedBy = IntervaloDataValidoValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IntervaloDataValido {
    String message() default "Intervalo de datas inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String inicio();
    String fim();
}

