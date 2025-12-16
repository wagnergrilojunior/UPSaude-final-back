package com.upsaude.validation.validator;

import com.upsaude.validation.annotation.CEPValido;
import com.upsaude.validation.util.CepUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CEPValidoValidator implements ConstraintValidator<CEPValido, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return CepUtil.isCepValido(value);
    }
}
