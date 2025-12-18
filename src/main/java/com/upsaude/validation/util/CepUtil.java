package com.upsaude.validation.util;

public final class CepUtil {

    private CepUtil() {}

    public static String somenteDigitos(CharSequence value) {
        return DocumentoUtil.somenteDigitos(value);
    }

    public static boolean isCepValido(CharSequence value) {
        if (DocumentoUtil.isBlank(value)) return true;
        String digits = somenteDigitos(value);
        return DocumentoUtil.somenteNumerosEComprimento(digits, 8);
    }
}
