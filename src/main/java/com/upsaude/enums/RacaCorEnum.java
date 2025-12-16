package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum RacaCorEnum {
    BRANCA(1, "Branca"),
    PRETA(2, "Preta"),
    PARDA(3, "Parda"),
    AMARELA(4, "Amarela"),
    INDIGENA(5, "Indígena"),
    IGNORADO(9, "Ignorado"),
    NAO_INFORMADO(99, "Não informado");

    private final Integer codigo;
    private final String descricao;

    RacaCorEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static RacaCorEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static RacaCorEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
