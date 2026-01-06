package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum TipoIdentificadorEnum {
    CPF(1, "CPF"),
    CNS(2, "CNS"),
    RG(3, "RG"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    TipoIdentificadorEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoIdentificadorEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static TipoIdentificadorEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toUpperCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
