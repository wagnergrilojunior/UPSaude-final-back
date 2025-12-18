package com.upsaude.validation.validator;

import com.upsaude.validation.annotation.SiteValido;
import com.upsaude.validation.util.UrlUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SiteValidoValidator implements ConstraintValidator<SiteValido, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return UrlUtil.isSiteValido(value);
    }
}
