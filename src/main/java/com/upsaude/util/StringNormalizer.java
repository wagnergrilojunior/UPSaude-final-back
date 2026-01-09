package com.upsaude.util;

import java.util.Locale;

/**
 * Utility para normalização de strings.
 * Usado principalmente para normalização de substâncias em alergias.
 */
public class StringNormalizer {

    /**
     * Normaliza uma substância para comparação.
     * Aplica: trim, lowercase (pt-BR), remove múltiplos espaços.
     * 
     * @param substancia Substância a ser normalizada
     * @return Substância normalizada ou null se entrada for null
     */
    public static String normalizeSubstancia(String substancia) {
        if (substancia == null) {
            return null;
        }
        return substancia.trim()
                .toLowerCase(Locale.forLanguageTag("pt-BR"))
                .replaceAll("\\s+", " ");
    }
}

