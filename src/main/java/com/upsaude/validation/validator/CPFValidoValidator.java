package com.upsaude.validation.validator;

import com.upsaude.validation.annotation.CPFValido;
import com.upsaude.validation.util.DocumentoUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidoValidator implements ConstraintValidator<CPFValido, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return DocumentoUtil.isCpfValido(value);
    }
}
