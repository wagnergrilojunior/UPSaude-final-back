package com.upsaude.validation.validator;

import com.upsaude.validation.annotation.CelularValido;
import com.upsaude.validation.util.TelefoneUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CelularValidoValidator implements ConstraintValidator<CelularValido, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return TelefoneUtil.isCelularValido(value);
    }
}
