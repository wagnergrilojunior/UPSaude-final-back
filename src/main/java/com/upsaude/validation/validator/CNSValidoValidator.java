package com.upsaude.validation.validator;

import com.upsaude.validation.annotation.CNSValido;
import com.upsaude.validation.util.DocumentoUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNSValidoValidator implements ConstraintValidator<CNSValido, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return DocumentoUtil.isCnsValido(value);
    }
}
