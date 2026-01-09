package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum OrigemIdentificadorEnum {
    UPSAUDE(1, "UPSaúde"),
    CADSUS(2, "CADSUS"),
    ESUS(3, "e-SUS APS"),
    RNDS(4, "RNDS"),
    OUTRO(99, "Outro");

    private final Integer codigo;
    private final String descricao;

    OrigemIdentificadorEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static OrigemIdentificadorEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static OrigemIdentificadorEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toUpperCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toUpperCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}
