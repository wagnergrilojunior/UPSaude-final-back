package com.upsaude.validation;

import com.upsaude.validation.validator.EmailValidoValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidacaoEmailTest {

    @Test
    void deveValidarEmailComCommonsValidator() {
        EmailValidoValidator v = new EmailValidoValidator();
        v.initialize(null);
        ConstraintValidatorContext ctx = null;

        assertTrue(v.isValid(null, ctx));
        assertTrue(v.isValid("", ctx));
        assertTrue(v.isValid("teste@exemplo.com", ctx));
        assertTrue(v.isValid("nome.sobrenome+tag@exemplo.com.br", ctx));

        assertFalse(v.isValid("invalido", ctx));
        assertFalse(v.isValid("a@com", ctx)); // TLD-only
        assertFalse(v.isValid("a@localhost", ctx)); // local não permitido
    }
}
