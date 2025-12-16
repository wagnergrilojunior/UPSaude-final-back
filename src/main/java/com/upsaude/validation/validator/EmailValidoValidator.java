package com.upsaude.validation.validator;

import com.upsaude.validation.annotation.EmailValido;
import com.upsaude.validation.util.DocumentoUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.EmailValidator;

public class EmailValidoValidator implements ConstraintValidator<EmailValido, CharSequence> {

    private EmailValidator validator;

    @Override
    public void initialize(EmailValido constraintAnnotation) {
        // Não permite local (user@localhost) nem TLD-only (user@com)
        this.validator = EmailValidator.getInstance(false, false);
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (DocumentoUtil.isBlank(value)) return true;
        String email = value.toString().trim();
        return validator.isValid(email);
    }
}
