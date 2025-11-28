package com.upsaude.enums;

import java.util.Arrays;
import java.util.Locale;

public enum OrientacaoSexualEnum {
    HETEROSSEXUAL(1, "Heterossexual"),
    HOMOSSEXUAL(2, "Homossexual"),
    BISSEXUAL(3, "Bissexual"),
    OUTRO(99, "Outro"),
    NAO_INFORMADO(9, "Não informado");

    private final Integer codigo;
    private final String descricao;

    OrientacaoSexualEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static OrientacaoSexualEnum fromCodigo(Integer codigo) {
        if (codigo == null) return null;
        return Arrays.stream(values())
                .filter(v -> v.codigo.equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public static OrientacaoSexualEnum fromDescricao(String descricao) {
        if (descricao == null) return null;
        String d = descricao.trim().toLowerCase(Locale.ROOT);
        return Arrays.stream(values())
                .filter(v -> v.descricao.toLowerCase(Locale.ROOT).equals(d))
                .findFirst()
                .orElse(null);
    }
}

