package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de zona do domicílio conforme padrão SUS/e-SUS.
 * Utilizado para identificação geográfica e planejamento de ações de saúde pública.
 * A zona determina estratégias diferentes de atendimento (urbana, rural, periurbana).
 */
public enum ZonaDomicilioEnum {
    URBANA(1, "Urbana"),
    RURAL(2, "Rural"),
    PERIURBANA(3, "Periurbana");

    private final Integer codigo;
    private final String descricao;

    ZonaDomicilioEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static ZonaDomicilioEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static ZonaDomicilioEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

