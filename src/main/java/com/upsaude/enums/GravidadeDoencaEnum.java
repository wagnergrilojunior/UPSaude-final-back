package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

/**
 * Enum para classificação de gravidade/severidade de doenças.
 *
 * @author UPSaúde
 */
public enum GravidadeDoencaEnum {
    LEVE(1, "Leve"),
    MODERADA(2, "Moderada"),
    GRAVE(3, "Grave"),
    MUITO_GRAVE(4, "Muito Grave"),
    CRITICA(5, "Crítica");

    private final Integer codigo;
    private final String descricao;

    GravidadeDoencaEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static GravidadeDoencaEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static GravidadeDoencaEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

