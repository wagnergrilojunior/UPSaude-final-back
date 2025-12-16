package com.upsaude.validation.validator;

import com.upsaude.validation.annotation.TelefoneValido;
import com.upsaude.validation.util.TelefoneUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneValidoValidator implements ConstraintValidator<TelefoneValido, CharSequence> {

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        // Compatibilidade com o projeto atual: aceita 10 (fixo) OU 11 (celular)
        return TelefoneUtil.isTelefoneOuCelularValido(value);
    }
}
