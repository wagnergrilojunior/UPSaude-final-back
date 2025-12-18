package com.upsaude.validation.util;

public final class TelefoneUtil {

    private TelefoneUtil() {}

    public static String somenteDigitos(CharSequence value) {
        return DocumentoUtil.somenteDigitos(value);
    }

    public static boolean isBlank(CharSequence value) {
        return DocumentoUtil.isBlank(value);
    }

    public static boolean isDddValido(String digits) {
        if (digits == null || digits.length() < 2) return false;
        int ddd = (digits.charAt(0) - '0') * 10 + (digits.charAt(1) - '0');
        return ddd >= 11 && ddd <= 99;
    }

    /**
     * Telefone fixo: DDD (2) + número (8) = 10 dígitos.
     */
    public static boolean isTelefoneFixoValido(CharSequence value) {
        if (isBlank(value)) return true;
        String digits = somenteDigitos(value);
        if (!DocumentoUtil.somenteNumerosEComprimento(digits, 10)) return false;
        if (!isDddValido(digits)) return false;

        // número (sem DDD)
        char primeiro = digits.charAt(2);
        // No Brasil, fixo tende a iniciar em 2-5 (evita 0/1/9)
        return primeiro >= '2' && primeiro <= '5';
    }

    /**
     * Celular: DDD (2) + 9 + número (8) = 11 dígitos.
     */
    public static boolean isCelularValido(CharSequence value) {
        if (isBlank(value)) return true;
        String digits = somenteDigitos(value);
        if (!DocumentoUtil.somenteNumerosEComprimento(digits, 11)) return false;
        if (!isDddValido(digits)) return false;
        // primeiro dígito do assinante deve ser 9
        return digits.charAt(2) == '9';
    }

    /**
     * Telefone genérico (compatibilidade): aceita 10 (fixo) OU 11 (celular).
     * Mantém o comportamento atual onde vários campos aceitam 10 ou 11 dígitos.
     */
    public static boolean isTelefoneOuCelularValido(CharSequence value) {
        if (isBlank(value)) return true;
        String digits = somenteDigitos(value);
        if (digits == null) return false;
        if (digits.length() == 10) return isTelefoneFixoValido(digits);
        if (digits.length() == 11) return isCelularValido(digits);
        return false;
    }
}
