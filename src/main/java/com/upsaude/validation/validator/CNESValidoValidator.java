package com.upsaude.validation.validator;

import com.upsaude.validation.annotation.CNESValido;
import com.upsaude.validation.util.DocumentoUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNESValidoValidator implements ConstraintValidator<CNESValido, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return DocumentoUtil.isCnesValido(value);
    }
}
