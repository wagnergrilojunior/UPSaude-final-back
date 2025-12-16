package com.upsaude.validation.validator;

import com.upsaude.validation.annotation.IntervaloDataValido;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class IntervaloDataValidoValidator implements ConstraintValidator<IntervaloDataValido, Object> {

    private String inicioField;
    private String fimField;

    @Override
    public void initialize(IntervaloDataValido constraintAnnotation) {
        this.inicioField = constraintAnnotation.inicio();
        this.fimField = constraintAnnotation.fim();
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        Object inicio = getFieldValue(value, inicioField);
        Object fim = getFieldValue(value, fimField);

        if (inicio == null || fim == null) return true;

        if (!(inicio instanceof Comparable) || !(fim instanceof Comparable)) {
            // tipo não suportado: não quebra o request, mas considera inválido para evitar inconsistência silenciosa
            return false;
        }

        try {
            Comparable cInicio = (Comparable) inicio;
            Comparable cFim = (Comparable) fim;
            return cFim.compareTo(cInicio) >= 0;
        } catch (ClassCastException e) {
            return false;
        }
    }

    private Object getFieldValue(Object target, String fieldName) {
        if (fieldName == null || fieldName.isBlank()) return null;
        Class<?> c = target.getClass();
        while (c != null && c != Object.class) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f.get(target);
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return null;
    }
}

