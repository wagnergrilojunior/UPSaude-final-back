package com.upsaude.validation.validator;

import com.upsaude.validation.annotation.CNPJValido;
import com.upsaude.validation.util.DocumentoUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidoValidator implements ConstraintValidator<CNPJValido, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return DocumentoUtil.isCnpjValido(value);
    }
}
