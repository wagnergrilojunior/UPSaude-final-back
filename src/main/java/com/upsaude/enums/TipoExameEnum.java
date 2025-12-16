package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoExameEnum {
    LABORATORIAL(1, "Laboratorial"),
    IMAGEM(2, "Imagem"),
    FUNCIONAL(3, "Funcional"),
    PATOLOGIA(4, "Patologia"),
    ENDOSCOPIA(5, "Endoscopia"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoExameEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoExameEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoExameEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
